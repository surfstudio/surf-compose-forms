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
package ru.surfstudio.compose.forms.fields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import ru.surfstudio.compose.forms.R
import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.states.PhoneStateValidate

/**
 * Number form field
 *
 * @param modifier modifier to apply to this layout node.
 * @param enabled controls the enabled state of the TextField.
 * @param label the optional label to be displayed.
 * @param textStyle Styling configuration for a Text.
 * @param imeAction Signals the keyboard what type of action should be displayed. It is not guaranteed if the keyboard will show the requested action.
 * @param keyboardActions The KeyboardActions class allows developers to specify actions that will be triggered in response to users triggering IME action on the software keyboard.
 * @param colors TextFieldColors for settings colors
 * @param state remember with FormFieldState for management TextField.
 * @param filter allows you to filter out all characters except those specified in the string
 * @param maxLines the maximum height in terms of maximum number of visible lines.
 * @param singleLine field becomes a single horizontally scrolling text field instead of wrapping onto multiple lines.
 * @param clearStartUnfocused Clear input if its value is equal to mask start (aka placeholder) and unfocused
 * @param maxLength Maximum allowed field length.
 * @param mask +380 (###) ###-##-##, +7 (###) ###-##-##, +# (###) ###-##-##, ####-####-####-#### etc
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and the input text is empty
 * @param contentError the optional error to be displayed inside the text field container.
 *
 * @since 0.0.7
 * @author Vitaliy Zarubin
 */
@Composable
fun FormFieldNumber(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(id = R.string.form_fields_phone),
    textStyle: TextStyle = LocalTextStyle.current,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions(),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    state: FormFieldState = remember { PhoneStateValidate() },
    onValueChange: ((TextFieldValue) -> TextFieldValue)? = null,
    filter: String? = null,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    clearStartUnfocused: Boolean = false,
    maxLength: Int? = null,
    mask: String? = null,
    placeholder: String? = null,
    contentError: @Composable (() -> Unit)? = null,
) = FormField(
    modifier = modifier,
    enabled = enabled,
    label = label,
    textStyle = textStyle,
    imeAction = imeAction,
    keyboardActions = keyboardActions,
    colors = colors,
    state = state,
    onValueChange = onValueChange,
    filter = filter,
    maxLines = maxLines,
    singleLine = singleLine,
    clearStartUnfocused = clearStartUnfocused,
    maxLength = maxLength,
    mask = mask,
    placeholder = placeholder,
    keyboardType = KeyboardType.Number,
    contentError = contentError,
)