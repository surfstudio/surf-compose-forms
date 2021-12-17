package ru.surfstudio.compose.forms.sample

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ru.surfstudio.compose.forms.sample.sign_in.SignInBody
import ru.surfstudio.compose.forms.sample.theme.TestTheme

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MainScreen() {
    TestTheme {
        Surface {
            SignInBody()
        }
    }
}

@Preview("Light", device = Devices.PIXEL_2_XL)
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_2_XL)
@ExperimentalComposeUiApi
@Composable
fun MainScreenPreview() {
    MainScreen()
}