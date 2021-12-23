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
 
package ru.surfstudio.compose.forms.sample

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ru.surfstudio.compose.forms.other.DotsNumbers
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
        Surface(modifier = Modifier.fillMaxSize()) {
            DotsNumbers()
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