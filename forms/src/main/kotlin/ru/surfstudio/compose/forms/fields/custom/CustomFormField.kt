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

import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.surfstudio.compose.forms.R
import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.base.onValueChangeMask
import ru.surfstudio.compose.forms.emoji.EmojiUtils
import ru.surfstudio.compose.modifier.ifFalse
import ru.surfstudio.compose.modifier.ifTrue
import ru.surfstudio.compose.modifier.visible

/**
 * Custom FormField with extended settings
 *
 * @author Vitaliy Zarubin
 * @since 0.0.19
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomFormField(
    modifier: Modifier = Modifier,
    // ui settings
    @DrawableRes icVisibilityOff: Int = R.drawable.ic_visibility_off,
    @DrawableRes icVisibilityOn: Int = R.drawable.ic_visibility,
    colorDefault: Color = MaterialTheme.colors.onSurface,
    colorSecondary: Color = Color.Gray,
    colorError: Color = Color.Red,
    colorLine: Color = Color.LightGray,
    cursorBrush: Brush = SolidColor(colorDefault),
    // field settings
    maxLines: Int = 1,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardType: KeyboardType = KeyboardType.Text,
    // field custom settings
    fieldLabel: String,
    fieldIsPassword: Boolean = false,
    fieldIsShowLength: Boolean = false,
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
    filterEmoji: Boolean = false,
    filterMask: String? = null,
    @IntRange(from = 1) filterMaxLength: Int = Int.MAX_VALUE,
    // forms
    formFieldValidate: Boolean = false,
    formFieldState: FormFieldState = remember { FormFieldState() },
    // actions
    onChange: () -> Unit = {},
    onFocusChange: (FocusState, Boolean) -> Unit = { state, isHashError -> },
) {

    val localDensity = LocalDensity.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // width line
    val strokeWidth = object {
        val minHeight = 1.dp
        val maxHeight = 2.dp
    }

    var sizeIcon: Dp by remember { mutableStateOf(0.dp) }
    var isFixError: Boolean by remember { mutableStateOf(false) }
    var isPressButton: Boolean by remember { mutableStateOf(false) }
    var isVisibilityPass: Boolean by remember { mutableStateOf(false) }
    var isChangeField: Boolean by remember { mutableStateOf(false) }
    var isFocusedField: Boolean by remember { mutableStateOf(false) }
    var strokeWidthLine: Dp by remember { mutableStateOf(strokeWidth.minHeight) }

    val isError: () -> Boolean = {
        isPressButton && formFieldState.hasErrors ||
                (!isFocusedField || isFocusedField && isFixError) &&
                formFieldState.hasErrors && isChangeField
    }

    LaunchedEffect(formFieldValidate) {
        if (formFieldValidate) {
            isPressButton = true
        }
    }

    Column(modifier = modifier) {
        Box {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .visible(
                            isFocusedField || formFieldState
                                .getValue()
                                .isNotEmpty()
                        )
                        .padding(bottom = 4.dp),
                    text = fieldLabel,
                    color = colorSecondary,
                    style = topLabelTextStyle
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .ifTrue(fieldEndIcon != null || fieldIsPassword) { then(padding(end = sizeIcon + 6.dp)) }
                        .focusRequester(formFieldState.focus)
                        .bringIntoViewRequester(formFieldState.relocation)
                        .padding(bottom = 7.dp)
                        .onFocusEvent { focusState ->
                            if (focusState.isFocused) {
                                scope.launch {
                                    delay(300) // keyboard change
                                    formFieldState.bringIntoView()
                                }
                            }
                        }
                        .onFocusChanged { focusState ->
                            // on change state focus
                            onFocusChange.invoke(focusState, isError.invoke())
                            // change focus state
                            isFocusedField = focusState.isFocused
                            // fix error
                            if (!isFocusedField && formFieldState.hasErrors) {
                                isFixError = true
                            }
                            // with line
                            strokeWidthLine =
                                if (isFocusedField) strokeWidth.maxHeight else strokeWidth.minHeight
                            if (focusState.isFocused) {
                                // change by mask
                                filterMask?.let {
                                    // mask
                                    formFieldState.text =
                                        onValueChangeMask.invoke(
                                            filterMask,
                                            formFieldState,
                                            formFieldState.text
                                        )
                                }
                            }
                        },
                    readOnly = readOnly,
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    enabled = enabled,
                    cursorBrush = cursorBrush,
                    visualTransformation = if (!fieldIsPassword || isVisibilityPass) VisualTransformation.None else PasswordVisualTransformation(),
                    textStyle = fieldTextStyle.copy(color = if (enabled) colorDefault else colorSecondary),
                    keyboardActions = keyboardActions,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = imeAction,
                        keyboardType = if (fieldIsPassword) KeyboardType.Password else keyboardType
                    ),
                    value = formFieldState.text,
                    onValueChange = { textFieldValue ->
                        // filter
                        var value = filterChar?.let {
                            val filterWithMask =
                                filterMask?.let { filterMask + filterChar } ?: filterChar
                            textFieldValue.copy(
                                text = textFieldValue.text.filter { c ->
                                    filterWithMask.contains(c)
                                }
                            )
                        } ?: textFieldValue
                        // filter Emoji
                        if (filterEmoji) {
                            EmojiUtils.removeEmoji(value.text).let {
                                if (it.length != value.text.length) {
                                    value = value.copy(text = it)
                                }
                            }
                        }
                        // maxLength
                        value = if (value.text.length > filterMaxLength) {
                            value.copy(text = value.text.take(filterMaxLength))
                        } else {
                            value
                        }
                        // change by mask
                        filterMask?.let {
                            // mask
                            formFieldState.text =
                                onValueChangeMask.invoke(filterMask, formFieldState, value)
                        } ?: run {
                            // custom or default
                            formFieldState.text = value
                        }
                        // enable fix error
                        isFixError = false
                        // enable is change
                        isChangeField = true
                        // disable button
                        isPressButton = false
                        // on change
                        onChange.invoke()
                    }
                )
            }
            Box(
                modifier = Modifier
                    .onSizeChanged { sizeIcon = with(localDensity) { it.width.toDp() } }
                    .padding(top = 4.dp)
                    .align(Alignment.CenterEnd),
            ) {
                if (fieldIsPassword && formFieldState.getValue().isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .ifTrue(enabled) {
                                then(clickable { isVisibilityPass = !isVisibilityPass })
                            },
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isVisibilityPass)
                                    icVisibilityOn
                                else
                                    icVisibilityOff
                            ),
                            contentDescription = null,
                            tint = colorSecondary
                        )
                    }
                } else {
                    fieldEndIcon?.let {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .ifTrue(onEndIconClicked != null) {
                                    then(clickable { onEndIconClicked!!() })
                                }
                        ) {
                            Image(
                                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                                painter = fieldEndIcon,
                                colorFilter = fieldEndTint?.let { ColorFilter.tint(it) },
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .visible(
                        !isFocusedField && formFieldState
                            .getValue()
                            .isEmpty()
                    ),
                text = fieldLabel,
                color = colorSecondary,
                style = innerLabelTextStyle
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(strokeWidthLine)
                .background(
                    when {
                        isError() -> colorError
                        isFocusedField -> colorDefault
                        else -> colorLine
                    }
                )
        )
        Row(Modifier.fillMaxWidth()) {
            if (isError()) {
                Box(modifier = Modifier.weight(1f)) {
                    Text(
                        modifier = Modifier.align(Alignment.TopStart),
                        text = formFieldState.getError(context).orEmpty(),
                        color = colorError,
                        style = errorTextStyle
                    )
                }
            }
            if (fieldIsShowLength && formFieldState.getValue().isNotEmpty()) {
                Box(modifier = Modifier.ifFalse(isError()) { then(weight(1f)) }) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(start = 4.dp),
                        text = "${formFieldState.getValue().length}/$filterMaxLength",
                        color = colorSecondary,
                        style = counterTextStyle
                    )
                }
            }
        }
    }
}
