package ru.surfstudio.compose.forms.sample.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.surfstudio.compose.forms.fields.custom.CustomFieldPhone
import ru.surfstudio.compose.forms.fields.custom.CustomFieldText
import ru.surfstudio.compose.forms.fields.custom.CustomFormFieldPassword

@Composable
fun CustomForm() {
    val padding = 16.dp
    val listState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(start = padding, end = padding)
            .fillMaxHeight()
            .verticalScroll(listState)
    ) {
        Spacer(modifier = Modifier.padding(24.dp))
        CustomFieldPhone(modifier = Modifier.padding(top = 24.dp), fieldLabel = "Phone")
        CustomFormFieldPassword(modifier = Modifier.padding(top = 24.dp), fieldLabel = "Password")
        CustomFieldText(modifier = Modifier.padding(top = 24.dp), fieldLabel = "Text")
    }
}