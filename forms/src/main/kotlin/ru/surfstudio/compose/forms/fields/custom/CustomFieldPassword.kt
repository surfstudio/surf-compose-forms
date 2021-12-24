/**
 * Copyright © 2021 Surf. All rights reserved.
 */
package ru.surfstudio.compose.forms.fields.custom

import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ru.surfstudio.compose.forms.R
import ru.surfstudio.compose.forms.base.FormFieldState

private val DEFAULT_PASSWORD_FILTER =
    (('a'..'z') + ('A'..'Z') + ('0'..'9') + ".,:;?!*+%-<>@[]{}/\\_\$#")
        .joinToString("")

/**
 * Form field password
 *
 * @author Vitaliy Zarubin
 */
@Composable
fun CustomFormFieldPassword(
    fieldLabel: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Password,
    @DrawableRes icVisibilityOff: Int = R.drawable.ic_visibility_off,
    @DrawableRes icVisibilityOn: Int = R.drawable.ic_visibility,
    formFieldValidate: Boolean = true,
    formFieldState: FormFieldState = remember { FormFieldState() },
    enabled: Boolean = true,
    filterChar: String = DEFAULT_PASSWORD_FILTER,
    @IntRange(from = 1) filterMaxLength: Int = 20,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onChange: () -> Unit = {},
    onFocusChange: (FocusState, Boolean) -> Unit = { state, isHashError -> },
) = CustomFormField(
    modifier = modifier.fillMaxWidth(),
    fieldLabel = fieldLabel,
    fieldIsPassword = true,
    filterEmoji = true,
    filterChar = filterChar,
    keyboardType = keyboardType,
    icVisibilityOff = icVisibilityOff,
    icVisibilityOn = icVisibilityOn,
    formFieldValidate = formFieldValidate,
    filterMaxLength = filterMaxLength,
    formFieldState = formFieldState,
    enabled = enabled,
    imeAction = imeAction,
    keyboardActions = keyboardActions,
    onChange = onChange,
    onFocusChange = onFocusChange,
)
