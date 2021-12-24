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
package ru.surfstudio.compose.forms.fields.custom

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ru.surfstudio.compose.forms.base.FormFieldState

/**
 * Form field text
 *
 * @author Vitaliy Zarubin
 */
@Composable
fun CustomFieldText(
    fieldLabel: String,
    modifier: Modifier = Modifier,
    fieldIsShowLength: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    formFieldValidate: Boolean = true,
    formFieldState: FormFieldState = remember { FormFieldState() },
    maxLines: Int = 5,
    fieldEndIcon: Painter? = null,
    @IntRange(from = 1) filterMaxLength: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.None,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onChange: () -> Unit = {},
    onFocusChange: (FocusState, Boolean) -> Unit = { state, isHashError -> },
) = CustomFormField(
    modifier = modifier.fillMaxWidth(),
    fieldLabel = fieldLabel,
    fieldIsShowLength = fieldIsShowLength,
    keyboardType = keyboardType,
    formFieldValidate = formFieldValidate,
    formFieldState = formFieldState,
    maxLines = maxLines,
    fieldEndIcon = fieldEndIcon,
    filterMaxLength = filterMaxLength,
    enabled = enabled,
    imeAction = imeAction,
    keyboardActions = keyboardActions,
    onChange = onChange,
    onFocusChange = onFocusChange,
)
