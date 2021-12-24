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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.surfstudio.compose.forms.sample.custom.CustomForm
import ru.surfstudio.compose.forms.sample.dots.DotsScreen
import ru.surfstudio.compose.forms.sample.dots.DotsViewModel
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
    val navController = rememberNavController()
    NavHost(navController, startDestination = MainScreen.Start.route) {
        composable(route = MainScreen.Start.route) {
            StartScreen(navController)
        }
        composable(route = MainScreen.Forms.route) {
            FormsScreen()
        }
        composable(route = MainScreen.CustomForms.route) {
            CustomFormsScreen()
        }
        composable(route = MainScreen.Dots.route) {
            DotsScreen(DotsViewModel())
        }
    }
}

@Composable
fun StartScreen(navController: NavController) {
    TestTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { navController.navigate(MainScreen.Forms.route) }
                ) {
                    Text(text = "Show forms")
                }
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { navController.navigate(MainScreen.CustomForms.route) }
                ) {
                    Text(text = "Show custom forms")
                }
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { navController.navigate(MainScreen.Dots.route) }
                ) {
                    Text(text = "Show dots")
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun FormsScreen() {
    TestTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            SignInBody()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun CustomFormsScreen() {
    val listState = rememberScrollState()
    TestTheme {
        Surface {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxHeight()
                    .verticalScroll(listState)
            ) {
                Spacer(modifier = Modifier.padding(24.dp))

                CustomForm()

                Spacer(modifier = Modifier.padding(24.dp))
            }
        }
    }
}