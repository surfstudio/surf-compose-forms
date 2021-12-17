package ru.surfstudio.compose.forms.sample.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import ru.surfstudio.compose.forms.sample.R

@Composable
fun TestTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val darkColorPalette = darkColors(
        primary = colorResource(id = R.color.purple_200),
        primaryVariant = colorResource(id = R.color.purple_700),
        secondary = colorResource(id = R.color.teal_200)
    )

    val lightColorPalette = lightColors(
        primary = colorResource(id = R.color.purple_500),
        primaryVariant = colorResource(id = R.color.purple_700),
        secondary = colorResource(id = R.color.teal_200)
    )

    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}