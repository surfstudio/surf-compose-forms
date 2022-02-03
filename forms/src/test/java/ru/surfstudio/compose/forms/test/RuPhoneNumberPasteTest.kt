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
package ru.surfstudio.compose.forms.test

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import org.junit.Test
import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.base.onValueChangeMask
import ru.surfstudio.compose.forms.fields.custom.overload.RU_PHONE_MASK

class RuPhoneNumberPasteTest {

    private val emptyState = FormFieldState()
    private val placeHolderState = FormFieldState(RU_PHONE_MASK.substringBefore("#"))

    private val assertedResult = "+7 (950) 123 45 68"
    private val pastedNumbers = listOf(
        "+79501234568",
        "79501234568",
        "89501234568",
        "9501234568",
        "9501234568666"
    )

    @Test
    fun testRuPhonePaste() {
        listOf(
            emptyState,
            placeHolderState
        ).forEach { formState ->
            println("test state = \"${formState.getValue()}\"")
            pastedNumbers.forEach { number ->
                val result = onValueChangeMask(
                    mask = RU_PHONE_MASK,
                    formState = formState,
                    textFieldValue = TextFieldValue(number),
                    isFocused = false,
                    clearStartUnfocused = false
                )
                println("paste number $number, result = \"${result.text}\"")
                assert(result.text == assertedResult)
                assert(result.selection == TextRange(assertedResult.length, assertedResult.length))
            }
        }
    }
}