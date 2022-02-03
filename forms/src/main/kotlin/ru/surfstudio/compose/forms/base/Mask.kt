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
package ru.surfstudio.compose.forms.base

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

private const val SHORT_RU_PHONE_LENGTH = 10 // example: 9501234568

/**
 * Convert value to mask
 *
 * @since 0.0.5
 * @author Vitaliy Zarubin
 */
tailrec
fun mock(text: String, maskFirstInt: String?, valueClear: String, mask1: String): String {
    return if (valueClear.isEmpty() || text == maskFirstInt) mask1 else mock(
        text,
        maskFirstInt,
        valueClear.drop(1),
        mask1.replaceFirst("#", valueClear.first().toString())
    )
}

/**
 * Clearing a value from mask variables
 *
 * @since 0.0.5
 * @author Vitaliy Zarubin
 */
fun String.clearMask(mask: String): String {
    val value = mask.replace("""[^\d]+""".toRegex(), "")
    return if (this.length <= value.length || !this.contains(value)) {
        this
    } else {
        this.drop(value.length)
    }
}

/**
 * State events TextFieldValue
 *
 * @since 0.0.5
 * @author Vitaliy Zarubin
 */
enum class TextFieldState {
    REMOVE,
    ADDED,
    END,
    MOVE
}

/**
 * Check for start overwriting if the value is completed
 *
 * @since 0.0.30
 * @author Margarita Volodina
 */
val checkOverwrite: (String, FormFieldState, TextFieldValue) -> Boolean =
    { mask, formState, textFieldValue ->
        formState.getValue().length == mask.length &&
                textFieldValue.text.length > mask.length
    }

/**
 * Get state events from TextFieldValue
 *
 * @since 0.0.5
 * @author Vitaliy Zarubin
 */
val onValueChangeMaskState: (String, FormFieldState, TextFieldValue) -> TextFieldState =
    { mask, formState, textFieldValue ->
        val value = textFieldValue.text.take(mask.length)
        when {
            // disable overwrite completed value
            checkOverwrite(mask, formState, textFieldValue) -> TextFieldState.END
            formState.getValue().length > value.length -> TextFieldState.REMOVE
            formState.getValue().length < value.length -> TextFieldState.ADDED
            value.length == mask.length && textFieldValue.selection.start == textFieldValue.text.length -> TextFieldState.END
            else -> TextFieldState.MOVE
        }
    }

/**
 * Main job of providing field masking
 *
 * todo add specific for phone
 * todo add unit tests
 *
 * @since 0.0.5
 * @author Vitaliy Zarubin
 */
fun onValueChangeMask(
    mask: String,
    formState: FormFieldState,
    textFieldValue: TextFieldValue,
    isFocused: Boolean,
    clearStartUnfocused: Boolean
): TextFieldValue {
    val startMask = mask.substringBefore("#")
    val clearMask = mask.getDigitalString()

    val currentValue = formState.getValue()
    val newValue = textFieldValue.text

    // detect copy-paste for RU phone todo refactor in separate class
    val isPastedValue = newValue.length - currentValue.length > 1

    val value = if (isPastedValue) {
        val fixedValue = when {
            newValue.startsWith(startMask) -> newValue.drop(startMask.length)
            else -> newValue
        }.getDigitalString()
        if (fixedValue.length > SHORT_RU_PHONE_LENGTH && fixedValue.startsWith('8')) {
            fixedValue.replaceFirst('8', '7')
        } else {
            if (!fixedValue.startsWith('7')) {
                "7$fixedValue"
            } else {
                fixedValue.take(mask.length)
            }
        }
    } else {
        newValue.take(mask.length)
    }

    val state = onValueChangeMaskState.invoke(mask, formState, textFieldValue)

    val maskFirstInt =
        if (mask.firstOrNull() == '+' && mask[1] in '0'..'9') mask[1].toString() else null
    val clearValue = value.getDigitalString()

    return if (state == TextFieldState.MOVE && textFieldValue.selection.start <= startMask.length) {
        if (!clearStartUnfocused && (textFieldValue.selection.end - textFieldValue.selection.start) > 1) {
            textFieldValue
        } else {
            TextFieldValue(
                text = when {
                    !isFocused && clearStartUnfocused && value == startMask -> ""
                    else -> value
                },
                selection = TextRange(
                    startMask.length,
                    startMask.length
                )
            )
        }
    } else if (state == TextFieldState.REMOVE && (clearValue == clearMask || clearValue == "")) {
        TextFieldValue(
            text = "",
            selection = TextRange(0, 0)
        )
    } else {
        when (state) {
            TextFieldState.REMOVE, TextFieldState.ADDED, TextFieldState.MOVE -> clearValue.let { text ->
                mock(text, maskFirstInt, text.clearMask(mask), mask)
                    .substringBefore("#")
                    .dropLastWhile { it !in '0'..'9' && it != '(' }
                    .take(mask.length)
                    .let { mockText ->
                        TextFieldValue(
                            text = mockText,
                            selection = when (state) {
                                TextFieldState.ADDED, TextFieldState.REMOVE -> {
                                    when {
                                        isPastedValue || textFieldValue.selection.start >= value.length -> {
                                            TextRange(mockText.length, mockText.length)
                                        }
                                        else -> {
                                            val plus =
                                                if (textFieldValue.selection.start < startMask.length + 1) 1 else 0
                                            if (state == TextFieldState.ADDED) {
                                                TextRange(
                                                    textFieldValue.selection.start.plus(plus),
                                                    textFieldValue.selection.start.plus(plus)
                                                )
                                            } else {
                                                TextRange(
                                                    textFieldValue.selection.start.minus(plus)
                                                        .coerceAtLeast(0),
                                                    textFieldValue.selection.start.minus(plus)
                                                        .coerceAtLeast(0)
                                                )
                                            }
                                        }
                                    }
                                }
                                else -> textFieldValue.selection
                            }
                        )
                    }
            }
            TextFieldState.END -> {
                val checkOverwrite = checkOverwrite(mask, formState, textFieldValue)
                val diff = textFieldValue.text.length - mask.length
                TextFieldValue(
                    text = if (checkOverwrite) {
                        formState.getValue()
                    } else {
                        value.take(mask.length)
                    },
                    selection = if (checkOverwrite) {
                        // if overwrite detected, the position remains the same
                        TextRange(
                            textFieldValue.selection.start.minus(diff)
                                .coerceAtLeast(0),
                            textFieldValue.selection.start.minus(diff)
                                .coerceAtLeast(0)
                        )
                    } else {
                        textFieldValue.selection
                    }
                )
            }
        }
    }
}

private fun String.getDigitalString() = filter(Char::isDigit)