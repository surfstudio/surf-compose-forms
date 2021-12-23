package ru.surfstudio.compose.forms.sample

internal sealed class MainScreen(val route: String) {
    object Start : MainScreen("start")
    object Forms : MainScreen("forms")
    object Dots : MainScreen("dots")
}