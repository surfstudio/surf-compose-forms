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
package ru.surfstudio.compose.forms.validation

import android.content.Context
import ru.surfstudio.compose.forms.R
import android.util.Patterns

fun getErrorIsNotPhone(target: String) = when {
    !Patterns.PHONE.matcher(target).matches() -> { it: Context ->
        it.getString(R.string.validate_is_incorrect)
    }
    else -> null
}
