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
package ru.surfstudio.compose.forms.fields.custom.overload

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.fields.custom.CustomFormField

/**
 * Form field text
 *
 * @author Vitaliy Zarubin
 */
@Composable
fun CustomFieldText(
    fieldLabel: String,
    modifier: Modifier = Modifier,
    // ui settings
    colorDefault: Color = MaterialTheme.colors.onSurface,
    colorSecondary: Color = Color.Gray,
    colorError: Color = Color.Red,
    colorLine: Color = Color.LightGray,
    cursorBrush: Brush = SolidColor(colorDefault),
    // field settings
    fieldPlaceholder: String? = null,
    fieldIsShowLength: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    formFieldValidate: Boolean = true,
    clearStartUnfocused: Boolean = false,
    applyBringIntoViewRequester: Boolean = true,
    formFieldState: FormFieldState = remember { FormFieldState() },
    maxLines: Int = 5,
    fieldEndIcon: Painter? = null,
    fieldEndTint: Color? = colorSecondary,
    onEndIconClicked: (() -> Unit)? = null,
    // text styles
    topLabelTextStyle: TextStyle = LocalTextStyle.current,
    innerLabelTextStyle: TextStyle = LocalTextStyle.current,
    errorTextStyle: TextStyle = LocalTextStyle.current,
    counterTextStyle: TextStyle = LocalTextStyle.current,
    fieldTextStyle: TextStyle = TextStyle.Default,
    // filters settings
    filterChar: String? = null,
    filterEmoji: Boolean = true,
    filterMask: String? = null,
    @IntRange(from = 1) filterMaxLength: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.None,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onChange: () -> Unit = {},
    onFocusChange: (FocusState, Boolean) -> Unit = { state, isHashError -> },
) = CustomFormField(
    modifier = modifier.fillMaxWidth(),
    colorDefault = colorDefault,
    colorSecondary = colorSecondary,
    colorError = colorError,
    colorLine = colorLine,
    cursorBrush = cursorBrush,
    fieldLabel = fieldLabel,
    fieldPlaceholder = fieldPlaceholder,
    fieldIsShowLength = fieldIsShowLength,
    keyboardType = keyboardType,
    formFieldValidate = formFieldValidate,
    clearStartUnfocused = clearStartUnfocused,
    applyBringIntoViewRequester = applyBringIntoViewRequester,
    formFieldState = formFieldState,
    maxLines = maxLines,
    fieldEndIcon = fieldEndIcon,
    fieldEndTint = fieldEndTint,
    onEndIconClicked = onEndIconClicked,
    topLabelTextStyle = topLabelTextStyle,
    innerLabelTextStyle = innerLabelTextStyle,
    errorTextStyle = errorTextStyle,
    counterTextStyle = counterTextStyle,
    fieldTextStyle = fieldTextStyle,
    filterChar = filterChar,
    filterEmoji = filterEmoji,
    filterMask = filterMask,
    filterMaxLength = filterMaxLength,
    enabled = enabled,
    imeAction = imeAction,
    keyboardActions = keyboardActions,
    onChange = onChange,
    onFocusChange = onFocusChange,
)
