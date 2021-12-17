package ru.surfstudio.compose.forms.sample.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun SignInBody(
    loading: Boolean = false
) {
    val padding = 16.dp
    val listState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(start = padding, end = padding)
            .background(MaterialTheme.colors.background)
            .verticalScroll(listState)
    ) {

        Spacer(modifier = Modifier.padding(24.dp))

        SignInForm(
            loading = loading
        )

        Spacer(modifier = Modifier.padding(24.dp))
    }
}