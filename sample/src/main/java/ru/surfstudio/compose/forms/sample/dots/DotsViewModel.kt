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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val VALID_PIN_CODE = "123456"
const val PIN_CODE_LENGTH = 6

class DotsViewModel : ViewModel() {

    private val _isSucceed = MutableStateFlow(false)
    val isSucceed: StateFlow<Boolean> get() = _isSucceed.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()

    fun validate(pin: String) {
        if (pin.length == VALID_PIN_CODE.length) {
            if (pin == VALID_PIN_CODE) {
                _isSucceed.value = true
                clearError()
            } else {
                _isSucceed.value = false
                _error.value = "Invalid pin code"
                viewModelScope.launch {
                    delay(500L)
                    clearError()
                }
            }
        }
    }

    private fun clearError() {
        _error.value = null
    }
}