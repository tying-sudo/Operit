package com.ai.assistance.operit.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.ai.assistance.operit.data.preferences.UserPreferencesManager

/** 主题套装：Aurora（深海军蓝 + 青紫极光） */
val AuroraDarkColorScheme =
        darkColorScheme(
                primary = Color(0xFF22D3EE),
                onPrimary = Color(0xFF052530),
                primaryContainer = Color(0xFF164E63),
                onPrimaryContainer = Color(0xFFA5F3FC),
                secondary = Color(0xFF818CF8),
                onSecondary = Color(0xFF1E1B4B),
                secondaryContainer = Color(0xFF3730A3),
                onSecondaryContainer = Color(0xFFC7D2FE),
                tertiary = Color(0xFF67E8F9),
                onTertiary = Color(0xFF052530),
                tertiaryContainer = Color(0xFF155E75),
                onTertiaryContainer = Color(0xFFCFFAFE),
                background = Color(0xFF0A1120),
                onBackground = Color(0xFFE8EEF6),
                surface = Color(0xFF0D1A2E),
                onSurface = Color(0xFFE8EEF6),
                surfaceVariant = Color(0xFF16283F),
                onSurfaceVariant = Color(0xFF8FB4C9),
                surfaceTint = Color(0xFF22D3EE),
                surfaceContainerLowest = Color(0xFF060C16),
                surfaceContainerLow = Color(0xFF0A1424),
                surfaceContainer = Color(0xFF101E32),
                surfaceContainerHigh = Color(0xFF14253C),
                surfaceContainerHighest = Color(0xFF1A2E47),
                surfaceBright = Color(0xFF2A3F57),
                surfaceDim = Color(0xFF060B14),
                inverseSurface = Color(0xFFE8EEF6),
                inverseOnSurface = Color(0xFF0D1A2E),
                inversePrimary = Color(0xFF0891B2),
                outline = Color(0xFF3D5468),
                outlineVariant = Color(0xFF24374C),
                error = Color(0xFFF87171),
                onError = Color(0xFF450A0A),
                errorContainer = Color(0xFF7F1D1D),
                onErrorContainer = Color(0xFFFEE2E2),
                scrim = Color(0xFF000000),
        )

/** 主题套装：Material（浅青灰标准质感） */
val MaterialLightColorScheme =
        lightColorScheme(
                primary = Color(0xFF3A6875),
                onPrimary = Color(0xFFFFFFFF),
                primaryContainer = Color(0xFFDCEBF0),
                onPrimaryContainer = Color(0xFF0E2A33),
                secondary = Color(0xFF5B8A97),
                onSecondary = Color(0xFFFFFFFF),
                secondaryContainer = Color(0xFFE0F4F6),
                onSecondaryContainer = Color(0xFF17343D),
                tertiary = Color(0xFF4BA3B0),
                onTertiary = Color(0xFFFFFFFF),
                tertiaryContainer = Color(0xFFEAF4F7),
                onTertiaryContainer = Color(0xFF0F3237),
                background = Color(0xFFF4F7F8),
                onBackground = Color(0xFF22333C),
                surface = Color(0xFFFFFFFF),
                onSurface = Color(0xFF22333C),
                surfaceVariant = Color(0xFFD5E3E6),
                onSurfaceVariant = Color(0xFF4A6068),
                surfaceTint = Color(0xFF3A6875),
                surfaceContainerLowest = Color(0xFFFFFFFF),
                surfaceContainerLow = Color(0xFFF7FAFB),
                surfaceContainer = Color(0xFFEFF4F6),
                surfaceContainerHigh = Color(0xFFE9F0F2),
                surfaceContainerHighest = Color(0xFFE3ECEE),
                surfaceBright = Color(0xFFFFFFFF),
                surfaceDim = Color(0xFFD9E2E5),
                inverseSurface = Color(0xFF2A3B42),
                inverseOnSurface = Color(0xFFEAF2F4),
                inversePrimary = Color(0xFF8FC9D4),
                outline = Color(0xFF8AA4AB),
                outlineVariant = Color(0xFFD5E3E6),
                error = Color(0xFFBA1A1A),
                onError = Color(0xFFFFFFFF),
                errorContainer = Color(0xFFFFDAD6),
                onErrorContainer = Color(0xFF410002),
                scrim = Color(0xFF000000),
        )

/** 主题套装：Glass Dream（浅紫缎面 + 半透明玻璃表面） */
val GlassDreamColorScheme =
        lightColorScheme(
                primary = Color(0xFF8B7CF6),
                onPrimary = Color(0xFFFFFFFF),
                primaryContainer = Color(0xFFEDE9FE),
                onPrimaryContainer = Color(0xFF3B2E7A),
                secondary = Color(0xFF818CF8),
                onSecondary = Color(0xFFFFFFFF),
                secondaryContainer = Color(0xFFE0E7FF),
                onSecondaryContainer = Color(0xFF2E3A8C),
                tertiary = Color(0xFFC084FC),
                onTertiary = Color(0xFFFFFFFF),
                tertiaryContainer = Color(0xFFFDEAFF),
                onTertiaryContainer = Color(0xFF5B21B6),
                background = Color(0xFFF5F3FF),
                onBackground = Color(0xFF332F4D),
                surface = Color(0xCCFFFFFF),
                onSurface = Color(0xFF332F4D),
                surfaceVariant = Color(0x99FFFFFF),
                onSurfaceVariant = Color(0xFF6B6580),
                surfaceTint = Color(0xFF8B7CF6),
                surfaceContainerLowest = Color(0xE6FFFFFF),
                surfaceContainerLow = Color(0xD9FFFFFF),
                surfaceContainer = Color(0xCCFFFFFF),
                surfaceContainerHigh = Color(0xBFFFFFFF),
                surfaceContainerHighest = Color(0xB3FFFFFF),
                surfaceBright = Color(0xF2FFFFFF),
                surfaceDim = Color(0x99F3F1FF),
                inverseSurface = Color(0xFF3A3654),
                inverseOnSurface = Color(0xFFF1EFFF),
                inversePrimary = Color(0xFFC4B5FD),
                outline = Color(0xFFB8B2D9),
                outlineVariant = Color(0x66FFFFFF),
                error = Color(0xFFBA1A1A),
                onError = Color(0xFFFFFFFF),
                errorContainer = Color(0xFFFFDAD6),
                onErrorContainer = Color(0xFF410002),
                scrim = Color(0xFF000000),
        )

/** 返回主题套装对应的 ColorScheme；非套装（默认主题）返回 null */
fun presetColorScheme(preset: String): ColorScheme? =
        when (preset) {
            UserPreferencesManager.THEME_PRESET_AURORA -> AuroraDarkColorScheme
            UserPreferencesManager.THEME_PRESET_MATERIAL -> MaterialLightColorScheme
            UserPreferencesManager.THEME_PRESET_GLASS -> GlassDreamColorScheme
            else -> null
        }

/** 返回主题套装锁定的暗色开关；非套装（默认主题）返回 null */
fun presetDarkTheme(preset: String): Boolean? =
        when (preset) {
            UserPreferencesManager.THEME_PRESET_AURORA -> true
            UserPreferencesManager.THEME_PRESET_MATERIAL -> false
            UserPreferencesManager.THEME_PRESET_GLASS -> false
            else -> null
        }

/** 主题套装的标志性根背景层，铺满全屏，绘制在所有内容之下 */
@Composable
fun ThemePresetBackgroundLayer(preset: String, modifier: Modifier = Modifier) {
    when (preset) {
        UserPreferencesManager.THEME_PRESET_AURORA ->
                Canvas(modifier.fillMaxSize()) {
                    // 深海军蓝纵向渐变底（mockup: 175deg #0a1120 → #0d1a2e 55% → #0a1424）
                    drawRect(
                            brush =
                                    Brush.verticalGradient(
                                            0f to Color(0xFF0A1120),
                                            0.55f to Color(0xFF0D1A2E),
                                            1f to Color(0xFF0A1424),
                                    ),
                    )
                    val maxDim = size.maxDimension
                    // 顶部青/紫极光光斑（radial-gradient 边缘淡出，等效 CSS blur 光斑）
                    drawCircle(
                            brush =
                                    Brush.radialGradient(
                                            0f to Color(0x4722D3EE),
                                            1f to Color(0x0022D3EE),
                                            center =
                                                    Offset(
                                                            size.width * 0.05f,
                                                            -size.height * 0.08f),
                                            radius = maxDim * 0.45f,
                                    ),
                            radius = maxDim * 0.45f,
                            center = Offset(size.width * 0.05f, -size.height * 0.08f),
                    )
                    drawCircle(
                            brush =
                                    Brush.radialGradient(
                                            0f to Color(0x428163F1),
                                            1f to Color(0x008163F1),
                                            center =
                                                    Offset(
                                                            size.width * 1.02f,
                                                            -size.height * 0.05f),
                                            radius = maxDim * 0.5f,
                                    ),
                            radius = maxDim * 0.5f,
                            center = Offset(size.width * 1.02f, -size.height * 0.05f),
                    )
                }
        UserPreferencesManager.THEME_PRESET_MATERIAL ->
                Canvas(modifier.fillMaxSize()) { drawRect(color = Color(0xFFF4F7F8)) }
        UserPreferencesManager.THEME_PRESET_GLASS ->
                Canvas(modifier.fillMaxSize()) {
                    // 浅色缎面底（mockup: 160deg #fdeaff → #dfe7ff 45% → #e8f6ff）
                    drawRect(
                            brush =
                                    Brush.linearGradient(
                                            0f to Color(0xFFFDEAFF),
                                            0.45f to Color(0xFFDFE7FF),
                                            1f to Color(0xFFE8F6FF),
                                            start = Offset.Zero,
                                            end = Offset(size.width, size.height),
                                    ),
                    )
                    val maxDim = size.maxDimension
                    // 紫粉蓝四色 mesh 光斑
                    drawCircle(
                            brush =
                                    Brush.radialGradient(
                                            0f to Color(0xB3FFD6F5),
                                            1f to Color(0x00FFD6F5),
                                            center =
                                                    Offset(
                                                            size.width * 0.18f,
                                                            size.height * 0.12f),
                                            radius = maxDim * 0.5f),
                            radius = maxDim * 0.5f,
                            center = Offset(size.width * 0.18f, size.height * 0.12f))
                    drawCircle(
                            brush =
                                    Brush.radialGradient(
                                            0f to Color(0xB3C7D8FF),
                                            1f to Color(0x00C7D8FF),
                                            center =
                                                    Offset(
                                                            size.width * 0.85f,
                                                            size.height * 0.08f),
                                            radius = maxDim * 0.55f),
                            radius = maxDim * 0.55f,
                            center = Offset(size.width * 0.85f, size.height * 0.08f))
                    drawCircle(
                            brush =
                                    Brush.radialGradient(
                                            0f to Color(0xB3E5CCFF),
                                            1f to Color(0x00E5CCFF),
                                            center =
                                                    Offset(
                                                            size.width * 0.9f,
                                                            size.height * 0.6f),
                                            radius = maxDim * 0.55f),
                            radius = maxDim * 0.55f,
                            center = Offset(size.width * 0.9f, size.height * 0.6f))
                    drawCircle(
                            brush =
                                    Brush.radialGradient(
                                            0f to Color(0xB3C8F0FF),
                                            1f to Color(0x00C8F0FF),
                                            center =
                                                    Offset(
                                                            size.width * 0.1f,
                                                            size.height * 0.85f),
                                            radius = maxDim * 0.55f),
                            radius = maxDim * 0.55f,
                            center = Offset(size.width * 0.1f, size.height * 0.85f))
                }
    }
}
