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
package ru.surfstudio.compose.forms.states

import ru.surfstudio.compose.forms.base.FormFieldState
import ru.surfstudio.compose.forms.validation.getErrorIsBlank
import ru.surfstudio.compose.forms.validation.getErrorIsNotDomain
import ru.surfstudio.compose.forms.validation.getErrorIsNotEmail

/**
 * State with validate domain and required
 *
 * @since 0.0.2
 * @author Vitaliy Zarubin
 */
class DomainStateValidateRequired : FormFieldState(checkValid = { target: String ->
    listOfNotNull(
        getErrorIsBlank(target),
        getErrorIsNotDomain(target),
    )
})



