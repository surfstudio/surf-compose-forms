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
package ru.surfstudio.compose.forms.base

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha


/**
 * Modifier check bool is FALSE for set params
 */
internal inline fun Modifier.ifFalse(value: Boolean, block: Modifier.() -> Modifier): Modifier =
    if (!value) block.invoke(this) else this

/**
 * Modifier check bool is TRUE for set params
 */
internal inline fun Modifier.ifTrue(value: Boolean, block: Modifier.() -> Modifier): Modifier =
    if (value) block.invoke(this) else this

/**
 * Controlling element visibility based on transparency
 */
internal fun Modifier.visible(visibility: Boolean): Modifier =
    this.then(alpha(if (visibility) 1f else 0f))