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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ru.surfstudio.compose.forms.base.FormFieldState

/**
 * Form field phone
 *
 * @author Vitaliy Zarubin
 */
@Composable
fun CustomFieldPhone(
    fieldLabel: String,
    modifier: Modifier = Modifier,
    filterMask: String = "+7 (###) ### ## ##",
    keyboardType: KeyboardType = KeyboardType.Phone,
    formFieldValidate: Boolean = true,
    formFieldState: FormFieldState = remember { FormFieldState() },
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onChange: () -> Unit = {},
    onFocusChange: (FocusState, Boolean) -> Unit = { state, isHashError -> },
) = CustomFormField(
    modifier = modifier.fillMaxWidth(),
    fieldLabel = fieldLabel,
    filterMask = filterMask,
    keyboardType = keyboardType,
    formFieldValidate = formFieldValidate,
    formFieldState = formFieldState,
    enabled = enabled,
    imeAction = imeAction,
    keyboardActions = keyboardActions,
    onChange = onChange,
    onFocusChange = onFocusChange,
)
