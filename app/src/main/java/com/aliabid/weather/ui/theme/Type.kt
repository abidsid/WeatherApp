package com.aliabid.weather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.aliabid.weather.R

object AppFont {
    val fonts = FontFamily(
        Font(R.font.poppins_light),
        Font(R.font.poppins_italic, style = FontStyle.Italic),
        Font(R.font.poppins_light, weight = FontWeight.Light),
        Font(R.font.poppins_medium, weight = FontWeight.Medium),
        Font(R.font.poppins_bold, weight = FontWeight.Bold),
        Font(R.font.poppins_semibold, weight = FontWeight.SemiBold),
        Font(R.font.poppins_extrabold, weight = FontWeight.ExtraBold),
    )
}

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.fonts),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.fonts),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.fonts),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.fonts),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.fonts),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.fonts),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.fonts),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.fonts),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.fonts),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.fonts),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.fonts),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.fonts),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.fonts),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.fonts),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.fonts)
)