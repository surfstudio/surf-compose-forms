package ru.surfstudio.compose.forms.sample.sign_in

import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.base.FormStates
import ru.surfstudio.compose.forms.states.EmailStateValidateRequired

enum class SignInFieldsForm(val state: FormFieldState) : FormStates {
    SignInEmail(EmailStateValidateRequired()),
    SignInPassword(PasswordStateValidateRequired()),
    SignInNoEmoji(FormFieldState()),
    SignInPhoneUA(FormFieldState()),
    SignInPhoneRU(FormFieldState()),
    SignInCard(FormFieldState()),
}