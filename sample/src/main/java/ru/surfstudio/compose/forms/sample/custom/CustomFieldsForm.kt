package ru.surfstudio.compose.forms.sample.custom

import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.base.FormStates

/**
 * Form states
 *
 * @author Vitaliy Zarubin
 */
enum class CustomFieldsForm(val state: FormFieldState) : FormStates {
    CustomPhone(CustomStateValidateRequired()),
    CustomPassword(CustomStateValidateRequired()),
    CustomFname(CustomStateValidateRequired()),
    CustomLname(CustomStateValidateRequired()),
    CustomDate(CustomStateValidateRequired()),
    CustomExperience(CustomStateValidateRequired()),
    CustomBio(CustomStateValidateRequired()),
}