package com.example.todoapp.fragments

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class AppColors(
    tertiary: Color,
    disabled: Color,
    gray: Color,
    secondary: Color,
    overlay: Color,
    green: Color,
    blue: Color,
    red: Color,
    light_red: Color,
    separator: Color,
    black: Color,
    back_primary: Color,
    back_secondary: Color,
    back_elevated: Color,
    primary: Color,
    gray_light: Color,
    white: Color,

){
    var tertiary by mutableStateOf(tertiary)
        private set
    var disabled by mutableStateOf(disabled)
        private set
    var gray by mutableStateOf(gray)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var overlay by mutableStateOf(overlay)
        private set
    var green by mutableStateOf(green)
        private set
    var blue by mutableStateOf(blue)
        private set
    var red by mutableStateOf(red)
        private set
    var light_red by mutableStateOf(light_red)
        private set
    var separator by mutableStateOf(separator)
        private set
    var black by mutableStateOf(black)
        private set
    var back_primary by mutableStateOf(back_primary)
        private set
    var back_secondary by mutableStateOf(back_secondary)
        private set
    var back_elevated by mutableStateOf(back_elevated)
        private set
    var primary by mutableStateOf(primary)
        private set
    var gray_light by mutableStateOf(gray_light)
        private set
    var white by mutableStateOf(white)
        private set

    fun update(other: AppColors){
        tertiary = other.tertiary
        disabled = other.disabled
        gray = other.gray
        secondary = other.secondary
        overlay = other.overlay
        green = other.green
        blue = other.blue
        red = other.red
        light_red = other.light_red
        separator = other.separator
        black = other.black
        back_primary = other.back_primary
        back_secondary = other.back_secondary
        back_elevated = other.back_elevated
        primary = other.primary
        gray_light = other.gray_light
        white = other.white
    }

}

private val DarkColorScheme = AppColors(
    tertiary = dark_tertiary,
    disabled = dark_disabled,
    gray = dark_gray,
    secondary = dark_secondary,
    overlay = dark_overlay,
    green = dark_green,
    blue = dark_blue,
    red = dark_red,
    light_red = dark_light_red,
    separator = dark_separator,
    black = dark_black,
    back_primary = dark_back_primary,
    back_secondary = dark_back_secondary,
    back_elevated = dark_back_elevated,
    primary = dark_primary,
    gray_light = dark_gray_light,
    white = dark_white,
)


private val LightColorScheme = AppColors(
    tertiary = tertiary,
    disabled = disabled,
    gray = gray,
    secondary = secondary,
    overlay = overlay,
    green = green,
    blue = blue,
    red = red,
    light_red = light_red,
    separator = separator,
    black = black,
    back_primary = back_primary,
    back_secondary = back_secondary,
    back_elevated = back_elevated,
    primary = primary,
    gray_light = gray_light,
    white = white,
)

internal val ColorsCustom = staticCompositionLocalOf<AppColors> { error("No colors provided") }
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    ProvideAppColors(colors = colorScheme) {
        MaterialTheme(
            content = content
        )
    }
}
@Composable
fun ProvideAppColors(
    colors: AppColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(ColorsCustom provides colorPalette, content = content)
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = ColorsCustom.current

}
