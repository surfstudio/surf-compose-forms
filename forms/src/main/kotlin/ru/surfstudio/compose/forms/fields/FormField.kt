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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.base.TextFieldError
import ru.surfstudio.compose.forms.base.ifTrue
import ru.surfstudio.compose.forms.base.onValueChangeMask
import ru.surfstudio.compose.forms.emoji.EmojiUtils

/**
 * Default form field
 *
 * @param modifier modifier to apply to this layout node.
 * @param enabled controls the enabled state of the [TextField].
 * @param readOnly controls the editable state of the [TextField].
 * @param label the optional label to be displayed.
 * @param textStyle Styling configuration for a Text.
 * @param imeAction Signals the keyboard what type of action should be displayed. It is not guaranteed if the keyboard will show the requested action.
 * @param visualTransformation
 * @param keyboardActions The KeyboardActions class allows developers to specify actions that will be triggered in response to users triggering IME action on the software keyboard.
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field container
 * @param colors TextFieldColors for settings colors
 * @param state remember with FormFieldState for management TextField.
 * @param onValueChange the callback that is triggered when the input service updates values in [TextFieldValue].
 * @param filter allows you to filter out all characters except those specified in the string.
 * @param filterEmoji Prevent or Allow emoji input for KeyboardType.Text
 * @param clearStartUnfocused Clear input if its value is equal to mask start (aka placeholder) and unfocused
 * @param applyBringIntoViewRequester If `bringIntoViewRequester` modifier is used. True by default.
 * Should be disabled in some cases, for example, if a field is located in HorizontalPager
 * @param lines height in lines.
 * @param maxLines the maximum height in terms of maximum number of visible lines.
 * @param singleLine field becomes a single horizontally scrolling text field instead of wrapping onto multiple lines.
 * @param maxLength Maximum allowed field length.
 * @param mask +380 (###) ###-##-##, +7 (###) ###-##-##, +# (###) ###-##-##, ####-####-####-#### etc
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and the input text is empty
 * @param keyboardType keyboard type used to request an IME.
 * @param contentError the optional error to be displayed inside the text field container.
 *
 * @since 0.0.9
 * @author Vitaliy Zarubin
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun FormField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    state: FormFieldState = remember { FormFieldState() },
    onValueChange: ((TextFieldValue) -> TextFieldValue)? = null,
    filter: String? = null,
    filterEmoji: Boolean = false,
    clearStartUnfocused: Boolean = false,
    applyBringIntoViewRequester: Boolean = true,
    lines: Int? = null,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    maxLength: Int? = null,
    mask: String? = null,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    contentError: @Composable (() -> Unit)? = null,
) {

    val scope = rememberCoroutineScope()
    var isFocusedField: Boolean by remember { mutableStateOf(false) }

    val sizeDp = with(LocalDensity.current) {
        textStyle.fontSize.value.sp.toDp() + 3.dp /* space */
    }

    // clear focus if keyboard hide
    val ime = LocalWindowInsets.current.ime
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(ime.isVisible) {
        if (!ime.isVisible) {
            // clear focuses
            localFocusManager.clearFocus()
        }
    }

    TextField(
        maxLines = maxLines,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        value = state.text,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onValueChange = { textFieldValue ->
            // filter
            var value = filter?.let {
                val filterWithMask = mask?.let { mask + filter } ?: filter
                textFieldValue.copy(text = textFieldValue.text.filter { c ->
                    filterWithMask.contains(
                        c
                    )
                })
            } ?: textFieldValue

            // maxLength
            if (value.text.length > (maxLength ?: Int.MAX_VALUE)) {
                return@TextField
            }
            
            // filter Emoji
            if (filterEmoji) {
                EmojiUtils.removeEmoji(value.text).let {
                    if (it.length != value.text.length) {
                        value = value.copy(text = it)
                    }
                }
            }

            mask?.let {
                // mask
                state.text = onValueChangeMask(
                    mask = mask,
                    formState = state,
                    textFieldValue = value,
                    isFocused = isFocusedField,
                    clearStartUnfocused = clearStartUnfocused
                )
            } ?: run {
                // custom or default
                state.text = onValueChange?.invoke(value) ?: value
            }
        },
        label = if (label != null) {
            { Text(label) }
        } else null,
        placeholder = if (placeholder != null) {
            { Text(placeholder) }
        } else null,
        modifier = modifier
            .defaultMinSize(
                minHeight = if (lines != null) {
                    sizeDp
                        .times(lines)
                        .plus(40.dp /* body field */)
                } else Dp.Unspecified
            )
            .fillMaxWidth()
            .focusRequester(state.focus)
            .ifTrue(applyBringIntoViewRequester) { then(bringIntoViewRequester(state.relocation)) }
            .onFocusEvent { focusState ->
                isFocusedField = focusState.isFocused
                if (isFocusedField) {
                    scope.launch {
                        delay(300) // keyboard change
                        state.bringIntoView()
                    }
                }
                mask?.let {
                    // mask
                    state.text = onValueChangeMask(
                        mask = mask,
                        formState = state,
                        textFieldValue = state.text,
                        isFocused = isFocusedField,
                        clearStartUnfocused = clearStartUnfocused
                    )
                }
            },
        isError = state.hasErrors,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = keyboardActions,
        colors = colors
    )

    state.getError(LocalContext.current)?.apply {
        contentError?.invoke() ?: TextFieldError(text = this)
    }
}