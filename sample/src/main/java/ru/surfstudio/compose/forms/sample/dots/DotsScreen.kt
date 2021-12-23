/*
 * Copyright 2021 Surf LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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