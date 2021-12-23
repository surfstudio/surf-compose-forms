/**
 * Copyright © 2021 Surf. All rights reserved.
 */
package ru.surfstudio.compose.forms.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.surfstudio.compose.forms.base.FormFieldState

/**
 * Component with dots for code entry
 *
 * @author Vitaliy Zarubin
 */
@Composable
fun DotsNumbers(
    modifier: Modifier = Modifier,
    error: String? = null,
    isSuccess: Boolean = false,
    count: Int = 6,
    activeColor: Color = MaterialTheme.colors.onSurface,
    inactiveColor: Color = Color.Gray,
    errorColor: Color = Color.Red,
    succeedColor: Color = Color.Green,
    indicatorSize: Dp = 10.dp,
    spacing: Dp = 24.dp,
    indicatorShape: Shape = CircleShape,
    state: FormFieldState = remember { FormFieldState() },
    isFocusable: Boolean = true,
    showNumber: Boolean = true,
    numberTextStyle: TextStyle = MaterialTheme.typography.h5,
    onChange: (String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val pagerCount = count + 1
    var pagerState by remember { mutableStateOf(0) }
    val isError = error != null

    LaunchedEffect(error) {
        if (isError) {
            state.setValue("")
            onChange.invoke("")
        }
    }

    state.setMaxValue(count)

    val mActiveColor: Color = when {
        isSuccess -> succeedColor
        isError -> errorColor
        else -> activeColor
    }

    val mIndicatorSize: Color = if (isError) errorColor else inactiveColor

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier.height(52.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            BasicTextField(
                enabled = isFocusable,
                singleLine = true,
                textStyle = TextStyle.Default.copy(Color.Transparent),
                cursorBrush = SolidColor(Color.Transparent),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            state.positionToEnd()
                        }
                    }
                    .width((indicatorSize + spacing) * count - spacing)
                    .focusRequester(state.focus),
                value = state.text,
                onValueChange = { textFieldValue ->
                    if (textFieldValue.text.length < pagerCount) {
                        state.text = textFieldValue.copy(
                            text = textFieldValue.text.filter { it.isDigit() }
                        )
                        onChange.invoke(state.getValue())
                    }
                },
            )

            LaunchedEffect(state.getValue()) {
                scope.launch {
                    if (state.getValue().length < pagerCount) {
                        pagerState = state.getValue().length
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(pagerCount - 1) {
                    val indicatorBox = when (it) {
                        0, pagerCount - 2 -> {
                            Modifier
                                .width(spacing / 2 + indicatorSize)
                                .height(spacing + indicatorSize + 7.dp)
                        }
                        else -> {
                            Modifier
                                .width(spacing + indicatorSize)
                                .height(spacing + indicatorSize + 7.dp)
                        }
                    }

                    Box(indicatorBox) {

                        val indicatorModifier = when (it) {
                            0 ->
                                Modifier
                                    .size(width = indicatorSize, height = indicatorSize)
                                    .align(Alignment.CenterStart)
                            pagerCount - 2 ->
                                Modifier
                                    .size(width = indicatorSize, height = indicatorSize)
                                    .align(Alignment.CenterEnd)
                            else ->
                                Modifier
                                    .size(width = indicatorSize, height = indicatorSize)
                                    .align(Alignment.Center)
                        }

                        if (it <= pagerState - 1 && state.getValue()
                                .isNotEmpty() && state.getValue().length > it && showNumber
                        ) {
                            Box(
                                when (it) {
                                    0 ->
                                        Modifier
                                            .width(16.dp)
                                            .align(Alignment.TopStart)
                                    pagerCount - 2 ->
                                        Modifier
                                            .width(16.dp)
                                            .align(Alignment.TopEnd)
                                    else ->
                                        Modifier
                                            .width(16.dp)
                                            .align(Alignment.TopCenter)
                                }
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.TopStart),
                                    color = mActiveColor,
                                    style = numberTextStyle,
                                    text = state.getValue()[it].toString()
                                )
                            }
                        } else {
                            Box(
                                indicatorModifier
                                    .background(
                                        color = if (it <= pagerState - 1 && !showNumber)
                                            mActiveColor else mIndicatorSize,
                                        shape = indicatorShape
                                    )
                            )
                        }
                    }
                }
            }
        }

        error?.let {
            Spacer(modifier = Modifier.size(4.dp))

            Text(
                color = errorColor,
                textAlign = TextAlign.Center,
                text = error,
            )
        } ?: Spacer(modifier = Modifier.size(22.dp))
    }
}
