package com.ai.assistance.operit.core.tools.ocr

import android.content.Context
import android.graphics.BitmapFactory
import com.ai.assistance.operit.core.tools.AIToolHandler
import com.ai.assistance.operit.core.tools.StringResultData
import com.ai.assistance.operit.core.tools.packTool.PackageManager
import com.ai.assistance.operit.data.model.AITool
import com.ai.assistance.operit.data.model.ToolParameter
import com.ai.assistance.operit.util.AppLogger
import com.ai.assistance.operit.util.OCRUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * 识图优先级路由：开关开启时，图片文字识别优先调用智谱 GLM-OCR 沙盒包
 * （com.operit.zhipu_ocr），失败或未配置时自动回退到本地 ML Kit OCR。
 */
object OcrPriorityRouter {
    private const val TAG = "OcrPriorityRouter"
    private const val PREFS_NAME = "ocr_priority"
    private const val KEY_ZHIPU_PRIORITY_ENABLED = "zhipu_priority_enabled"

    const val ZHIPU_OCR_PACKAGE_NAME = "zhipu_ocr"
    private const val ZHIPU_OCR_TOOL_NAME = "ocr_image"

    const val SOURCE_ZHIPU_GLM_OCR = "zhipu_glm_ocr"
    const val SOURCE_LOCAL_MLKIT = "local_mlkit"

    data class Recognition(
            val text: String,
            val source: String,
            val fallbackReason: String? = null,
            val decodeFailed: Boolean = false,
    )

    fun isZhipuPriorityEnabled(context: Context): Boolean {
        return context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_ZHIPU_PRIORITY_ENABLED, false)
    }

    fun setZhipuPriorityEnabled(context: Context, enabled: Boolean) {
        context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_ZHIPU_PRIORITY_ENABLED, enabled)
                .apply()
        AppLogger.i(TAG, "智谱OCR优先开关已切换: $enabled")
    }

    /** 沙盒包已安装且提供 ocr_image 工具（与是否启用无关）。 */
    fun isZhipuOcrPackageInstalled(context: Context): Boolean {
        return runCatching {
            val packageManager = PackageManager.getInstance(
                    context,
                    AIToolHandler.getInstance(context)
            )
            packageManager.getAvailablePackages()[ZHIPU_OCR_PACKAGE_NAME]
                    ?.tools
                    ?.any { it.name == ZHIPU_OCR_TOOL_NAME } == true
        }.getOrDefault(false)
    }

    fun isZhipuOcrPackageEnabled(context: Context): Boolean {
        return runCatching {
            PackageManager.getInstance(context, AIToolHandler.getInstance(context))
                    .isPackageEnabled(ZHIPU_OCR_PACKAGE_NAME)
        }.getOrDefault(false)
    }

    /**
     * 按当前开关路由图片文字识别：智谱 GLM-OCR 优先，失败回退本地 ML Kit。
     * @param imagePath 本地图片绝对路径
     */
    suspend fun recognizeText(
            context: Context,
            imagePath: String,
            quality: OCRUtils.Quality = OCRUtils.Quality.HIGH,
    ): Recognition {
        if (isZhipuPriorityEnabled(context)) {
            val zhipuOutcome = runCatching { recognizeWithZhipu(context, imagePath) }
            val zhipuText = zhipuOutcome.getOrNull()
            if (!zhipuText.isNullOrBlank()) {
                return Recognition(zhipuText, SOURCE_ZHIPU_GLM_OCR)
            }
            val reason =
                    zhipuOutcome.exceptionOrNull()?.message
                            ?: "智谱GLM-OCR未返回识别文本"
            AppLogger.w(TAG, "智谱GLM-OCR识别不可用，回退本地OCR: $reason")
            return recognizeLocally(context, imagePath, quality, reason)
        }
        return recognizeLocally(context, imagePath, quality, null)
    }

    private suspend fun recognizeLocally(
            context: Context,
            imagePath: String,
            quality: OCRUtils.Quality,
            fallbackReason: String?,
    ): Recognition {
        return withContext(Dispatchers.IO) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            if (bitmap == null) {
                Recognition("", SOURCE_LOCAL_MLKIT, fallbackReason, decodeFailed = true)
            } else {
                Recognition(
                        OCRUtils.recognizeText(context, bitmap, quality),
                        SOURCE_LOCAL_MLKIT,
                        fallbackReason
                )
            }
        }
    }

    private suspend fun recognizeWithZhipu(context: Context, imagePath: String): String {
        if (!isZhipuOcrPackageEnabled(context)) {
            throw IllegalStateException("智谱GLM-OCR插件未启用")
        }
        val toolHandler = AIToolHandler.getInstance(context)
        val result =
                withContext(Dispatchers.IO) {
                    toolHandler.executeTool(
                            AITool(
                                    name = "$ZHIPU_OCR_PACKAGE_NAME:$ZHIPU_OCR_TOOL_NAME",
                                    parameters =
                                            listOf(
                                                    ToolParameter(
                                                            name = "image_path",
                                                            value = imagePath
                                                    )
                                            )
                            )
                    )
                }
        if (!result.success) {
            throw IllegalStateException(
                    result.error?.take(300)?.ifBlank { null } ?: "智谱GLM-OCR调用失败"
            )
        }
        val raw = (result.result as? StringResultData)?.value.orEmpty()
        return extractMarkdownText(raw)
                ?: throw IllegalStateException("智谱GLM-OCR响应中没有识别文本")
    }

    /**
     * 包工具通过 complete({success, message, data}) 返回结果信封，
     * 识别文本位于 data.md_text；部分路径也可能直接返回纯文本。
     */
    private fun extractMarkdownText(raw: String): String? {
        val trimmed = raw.trim()
        if (trimmed.startsWith("{")) {
            return runCatching {
                val obj = JSONObject(trimmed)
                if (obj.has("md_text")) {
                    return@runCatching obj.optString("md_text").takeIf { it.isNotBlank() }
                }
                val dataObj =
                        when (val data = obj.opt("data")) {
                            is JSONObject -> data
                            is String ->
                                    if (data.trim().startsWith("{"))
                                            runCatching { JSONObject(data.trim()) }.getOrNull()
                                    else null
                            else -> null
                        }
                dataObj?.optString("md_text")?.takeIf { it.isNotBlank() }
            }.getOrNull()
        }
        return trimmed.takeIf { it.isNotBlank() }
    }
}
