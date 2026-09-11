package com.ai.assistance.operit.ui.features.settings.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ai.assistance.operit.R
import com.ai.assistance.operit.data.preferences.ThemePreferenceValues
import com.ai.assistance.operit.data.preferences.UserPreferencesManager
import com.ai.assistance.operit.ui.features.settings.screens.theme.ThemeEditorSession

@Composable
internal fun ThemeSettingsPresetSection(
    cardColors: CardColors,
    editorSession: ThemeEditorSession,
    themePresetInput: String,
) {
    ThemeSettingsSectionTitle(
        title = stringResource(id = R.string.theme_preset_title),
        icon = Icons.Default.Style,
    )

    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), colors = cardColors) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.theme_preset_desc),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ThemePresetCard(
                    name = stringResource(id = R.string.theme_preset_default),
                    preview = {
                        Box(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(Color(0xFFFFFFFF), Color(0xFF212121))
                                        )
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant,
                                        shape = RoundedCornerShape(10.dp),
                                    )
                        )
                    },
                    selected = themePresetInput == UserPreferencesManager.THEME_PRESET_DEFAULT,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        editorSession.update { values ->
                            values.withString(
                                "theme_preset",
                                UserPreferencesManager.THEME_PRESET_DEFAULT,
                            )
                        }
                    },
                )

                ThemePresetCard(
                    name = stringResource(id = R.string.theme_preset_aurora),
                    preview = {
                        Box(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFF22D3EE), Color(0xFF0D1A2E))
                                        )
                                    )
                        )
                    },
                    selected = themePresetInput == UserPreferencesManager.THEME_PRESET_AURORA,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        editorSession.update { values ->
                            applyAuroraPreset(values)
                        }
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ThemePresetCard(
                    name = stringResource(id = R.string.theme_preset_material),
                    preview = {
                        Box(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color(0xFF3A6875), Color(0xFFF4F7F8))
                                        )
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant,
                                        shape = RoundedCornerShape(10.dp),
                                    )
                        )
                    },
                    selected = themePresetInput == UserPreferencesManager.THEME_PRESET_MATERIAL,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        editorSession.update { values ->
                            applyMaterialPreset(values)
                        }
                    },
                )

                ThemePresetCard(
                    name = stringResource(id = R.string.theme_preset_glass),
                    preview = {
                        Box(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                Color(0xFFFDEAFF),
                                                Color(0xFFDFE7FF),
                                                Color(0xFFE8F6FF),
                                            )
                                        )
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant,
                                        shape = RoundedCornerShape(10.dp),
                                    )
                        )
                    },
                    selected = themePresetInput == UserPreferencesManager.THEME_PRESET_GLASS,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        editorSession.update { values ->
                            applyGlassPreset(values)
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun ThemePresetCard(
    name: String,
    preview: @Composable () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (selected) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
            ),
        border =
            if (selected) {
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            } else {
                null
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            preview()
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            )
        }
    }
}

private fun applyAuroraPreset(values: ThemePreferenceValues): ThemePreferenceValues {
    return values
        .withString("theme_preset", UserPreferencesManager.THEME_PRESET_AURORA)
        .withBoolean("use_system_theme", false)
        .withString("theme_mode", UserPreferencesManager.THEME_MODE_DARK)
        .withBoolean("use_custom_colors", false)
        .withBoolean("status_bar_transparent", true)
        .withBoolean("toolbar_transparent", true)
        .withBoolean("force_app_bar_content_color_enabled", true)
        .withString("app_bar_content_color_mode", UserPreferencesManager.APP_BAR_CONTENT_COLOR_MODE_LIGHT)
        .withBoolean("chat_header_transparent", true)
        .withBoolean("chat_input_transparent", true)
        .withBoolean("chat_input_liquid_glass", false)
        .withBoolean("chat_input_water_glass", false)
        .withBoolean("cursor_user_bubble_liquid_glass", false)
        .withBoolean("cursor_user_bubble_water_glass", false)
        .withBoolean("bubble_user_bubble_liquid_glass", false)
        .withBoolean("bubble_user_bubble_water_glass", false)
        .withBoolean("bubble_ai_bubble_liquid_glass", false)
        .withBoolean("bubble_ai_bubble_water_glass", false)
}

private fun applyMaterialPreset(values: ThemePreferenceValues): ThemePreferenceValues {
    return values
        .withString("theme_preset", UserPreferencesManager.THEME_PRESET_MATERIAL)
        .withBoolean("use_system_theme", false)
        .withString("theme_mode", UserPreferencesManager.THEME_MODE_LIGHT)
        .withBoolean("use_custom_colors", false)
        .withBoolean("status_bar_transparent", false)
        .withBoolean("toolbar_transparent", false)
        .withBoolean("force_app_bar_content_color_enabled", false)
        .withBoolean("chat_header_transparent", false)
        .withBoolean("chat_input_transparent", false)
        .withBoolean("chat_input_liquid_glass", false)
        .withBoolean("chat_input_water_glass", false)
        .withBoolean("cursor_user_bubble_liquid_glass", false)
        .withBoolean("cursor_user_bubble_water_glass", false)
        .withBoolean("bubble_user_bubble_liquid_glass", false)
        .withBoolean("bubble_user_bubble_water_glass", false)
        .withBoolean("bubble_ai_bubble_liquid_glass", false)
        .withBoolean("bubble_ai_bubble_water_glass", false)
}

private fun applyGlassPreset(values: ThemePreferenceValues): ThemePreferenceValues {
    return values
        .withString("theme_preset", UserPreferencesManager.THEME_PRESET_GLASS)
        .withBoolean("use_system_theme", false)
        .withString("theme_mode", UserPreferencesManager.THEME_MODE_LIGHT)
        .withBoolean("use_custom_colors", false)
        .withBoolean("status_bar_transparent", true)
        .withBoolean("toolbar_transparent", true)
        .withBoolean("force_app_bar_content_color_enabled", true)
        .withString("app_bar_content_color_mode", UserPreferencesManager.APP_BAR_CONTENT_COLOR_MODE_DARK)
        .withBoolean("chat_header_transparent", true)
        .withBoolean("chat_input_transparent", true)
        .withBoolean("chat_input_liquid_glass", true)
        .withBoolean("chat_input_water_glass", false)
        .withBoolean("cursor_user_bubble_liquid_glass", true)
        .withBoolean("cursor_user_bubble_water_glass", false)
        .withBoolean("bubble_user_bubble_liquid_glass", true)
        .withBoolean("bubble_user_bubble_water_glass", false)
        .withBoolean("bubble_ai_bubble_liquid_glass", true)
        .withBoolean("bubble_ai_bubble_water_glass", false)
        .withBoolean("bubble_user_use_image", false)
        .withBoolean("bubble_ai_use_image", false)
}
