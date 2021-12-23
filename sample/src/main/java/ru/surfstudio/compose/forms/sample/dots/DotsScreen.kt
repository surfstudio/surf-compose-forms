package ru.surfstudio.compose.forms.sample.dots

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.surfstudio.compose.forms.other.DotsNumbers
import ru.surfstudio.compose.forms.sample.theme.TestTheme

@Composable
fun DotsScreen(viewModel: DotsViewModel) {
    val isSucceed: Boolean by viewModel.isSucceed.collectAsState()
    val error: String? by viewModel.error.collectAsState()

    TestTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            DotsNumbers(
                isSuccess = isSucceed,
                error = error,
                count = PIN_CODE_LENGTH
            ) {
                viewModel.validate(it)
            }
        }
    }
}