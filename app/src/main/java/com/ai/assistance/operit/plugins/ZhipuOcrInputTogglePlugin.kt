package com.ai.assistance.operit.plugins

import com.ai.assistance.operit.R
import com.ai.assistance.operit.core.tools.AIToolHandler
import com.ai.assistance.operit.core.tools.ocr.OcrPriorityRouter
import com.ai.assistance.operit.core.tools.packTool.PackageManager
import com.ai.assistance.operit.ui.features.chat.components.style.input.common.InputMenuToggleDefinition
import com.ai.assistance.operit.ui.features.chat.components.style.input.common.InputMenuToggleHookParams
import com.ai.assistance.operit.ui.features.chat.components.style.input.common.InputMenuTogglePlugin
import com.ai.assistance.operit.ui.features.chat.components.style.input.common.InputMenuTogglePluginRegistry
import com.ai.assistance.operit.ui.features.chat.components.style.input.common.InputMenuToggleSlots

/**
 * 智谱GLM-OCR识图优先开关：显示在聊天输入菜单的“插件”分组中。
 * 开启后图片文字识别优先调用智谱GLM-OCR沙盒包，失败自动回退本地ML Kit OCR。
 * 仅在已安装 com.operit.zhipu_ocr 沙盒包时显示。
 */
object ZhipuOcrInputTogglePlugin : InputMenuTogglePlugin {
    override val id: String = "core_zhipu_ocr_priority"

    override fun createToggles(
            params: InputMenuToggleHookParams
    ): List<InputMenuToggleDefinition> {
        val context = params.context
        if (!OcrPriorityRouter.isZhipuOcrPackageInstalled(context)) {
            return emptyList()
        }
        val checked = OcrPriorityRouter.isZhipuPriorityEnabled(context)
        return listOf(
                InputMenuToggleDefinition(
                        id = "zhipu_ocr_priority",
                        titleRes = R.string.zhipu_ocr_toggle_title,
                        descriptionRes = R.string.zhipu_ocr_toggle_desc,
                        isChecked = checked,
                        isEnabled = OcrPriorityRouter.isZhipuOcrPackageEnabled(context),
                        slot = InputMenuToggleSlots.DEFAULT,
                        onToggle = {
                            OcrPriorityRouter.setZhipuPriorityEnabled(context, !checked)
                            InputMenuTogglePluginRegistry.notifyChanged()
                        }
                )
        )
    }
}
