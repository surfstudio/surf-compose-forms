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
package ru.surfstudio.compose.forms.sample.custom

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.base.FormFieldsState
import ru.surfstudio.compose.forms.fields.custom.CustomFieldPhone
import ru.surfstudio.compose.forms.fields.custom.CustomFieldText
import ru.surfstudio.compose.forms.fields.custom.CustomFormFieldPassword
import ru.surfstudio.compose.forms.sample.R
import ru.surfstudio.compose.forms.sample.custom.CustomFieldsForm.*
import ru.surfstudio.compose.forms.validation.getErrorIsBlank

/**
 * Validator & state
 *
 * @author Vitaliy Zarubin
 */
class CustomStateValidateRequired : FormFieldState(checkValid = { target: String ->
    listOfNotNull(
        getErrorIsBlank(target),
    )
})

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomForm() {

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val padding = 16.dp

    var showDialogDate by remember { mutableStateOf(false) }
    var isLoading: Boolean by remember { mutableStateOf(false) }

    // init form fields
    val formFields = remember {
        FormFieldsState().apply {
            add(CustomPhone, CustomPhone.state.default(""))
            add(CustomPassword, CustomPassword.state.default(""))
            add(CustomFname, CustomFname.state.default(""))
            add(CustomLname, CustomLname.state.default(""))
            add(CustomDate, CustomDate.state.default(""))
            add(CustomExperience, CustomExperience.state.default(""))
            add(CustomBio, CustomBio.state.default(""))
        }
    }

    // init is form validate
    val fromValidate by formFields.isValidate.collectAsState()

    // click submit
    val submitClick = {
        // validate before send
        formFields.validate()
        // check has errors
        if (!formFields.hasErrors()) {
            // clear focuses
            localFocusManager.clearFocus()
            // hide keyboard
            softwareKeyboardController?.hide()

            isLoading = true
            scope.launch {
                delay(2000)
                isLoading = false
            }
        }
    }

    Spacer(modifier = Modifier.size(padding))

    CustomFieldPhone(
        fieldLabel = "Phone",
        formFieldValidate = fromValidate,
        formFieldState = formFields.get(CustomPhone),
        enabled = !isLoading,
        imeAction = ImeAction.Next,
        keyboardActions = KeyboardActions(onNext = {
            formFields.get(CustomPassword).requestFocus()
        })
    )

    Spacer(modifier = Modifier.size(padding))

    // filed password
    CustomFormFieldPassword(
        fieldLabel = "Password",
        formFieldValidate = fromValidate,
        formFieldState = formFields.get(CustomPassword),
        enabled = !isLoading,
        imeAction = ImeAction.Next,
        keyboardActions = KeyboardActions(onNext = {
            formFields.get(CustomFname).requestFocus()
        })
    )

    Spacer(modifier = Modifier.size(padding))

    // filed Fname
    CustomFieldText(
        fieldLabel = "Fname",
        filterMaxLength = 10,
        formFieldValidate = fromValidate,
        formFieldState = formFields.get(CustomFname),
        enabled = !isLoading,
        imeAction = ImeAction.Next,
        keyboardActions = KeyboardActions(onNext = {
            formFields.get(CustomLname).requestFocus()
        })
    )

    Spacer(modifier = Modifier.size(padding))

    // filed Lname
    CustomFieldText(
        modifier = Modifier.fillMaxWidth(),
        fieldLabel = "Lname",
        filterMaxLength = 10,
        fieldIsShowLength = true,
        formFieldValidate = fromValidate,
        formFieldState = formFields.get(CustomLname),
        enabled = !isLoading,
        imeAction = ImeAction.Next,
        keyboardActions = KeyboardActions(onNext = {
            formFields.get(CustomDate).requestFocus()
        })
    )

    Spacer(modifier = Modifier.size(padding))

    // filed Date
    CustomFieldText(
        modifier = Modifier.fillMaxWidth(),
        fieldLabel = "Date",
        fieldEndIcon = painterResource(id = R.drawable.ic_default_date_range_24),
        formFieldValidate = fromValidate,
        formFieldState = formFields.get(CustomDate),
        enabled = !isLoading,
        imeAction = ImeAction.Next,
        keyboardActions = KeyboardActions(onNext = {
            formFields.get(CustomExperience).requestFocus()
        }),
        onFocusChange = { state, _ ->
            if (state.isFocused) {
                showDialogDate = true
            }
        }
    )

    Spacer(modifier = Modifier.size(padding))

    // filed Experience
    CustomFieldText(
        modifier = Modifier.fillMaxWidth(),
        fieldLabel = "Experience",
        formFieldValidate = fromValidate,
        formFieldState = formFields.get(CustomExperience),
        enabled = !isLoading
    )

    Spacer(modifier = Modifier.size(padding))

    // bio
    CustomFieldText(
        modifier = Modifier.fillMaxWidth(),
        fieldLabel = "Bio",
        filterMaxLength = 200,
        fieldIsShowLength = true,
        formFieldValidate = fromValidate,
        formFieldState = formFields.get(CustomBio),
        enabled = !isLoading
    )

    Spacer(modifier = Modifier.size(padding))

    // Submit button
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { submitClick.invoke() },
    ) {
        Text(
            text = stringResource(id = R.string.form_button_submit).uppercase(),
        )
    }

    // Date dialog
    if (showDialogDate) {
        Toast.makeText(context, "show date dialog", Toast.LENGTH_SHORT).show()
    }
}
