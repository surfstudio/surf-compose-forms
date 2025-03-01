## Compose Forms

![picture](https://github.com/surfstudio/surf-compose-forms/blob/master/data/just-image.png?raw=true)

Field state manager and basic set of validation, fields

## Connection

![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fartifactory.surfstudio.ru%2Fartifactory%2Flibs-release-local%2Fru%2Fsurfstudio%2Fcompose%2Fforms%2Fmaven-metadata.xml)

```gradle
repositories {
    maven("https://artifactory.surfstudio.ru/artifactory/libs-release-local")
}
dependencies {
    implementation("ru.surfstudio.compose:forms:${version}")
}
```

## Features:

### ![picture](https://github.com/google/material-design-icons/blob/master/png/action/build_circle/materialicons/18dp/1x/baseline_build_circle_black_18dp.png?raw=true) [State management TextField](https://keygenqt.github.io/compose-forms/stateTextField)
The library calls for encapsulating state management

### ![picture](https://github.com/google/material-design-icons/blob/master/png/action/check_circle/materialicons/18dp/1x/baseline_check_circle_black_18dp.png?raw=true) [Validation TextField](https://keygenqt.github.io/compose-forms/validationTextField)
You can add create custom states or use default states

### ![picture](https://github.com/google/material-design-icons/blob/master/png/action/grading/materialicons/18dp/1x/baseline_grading_black_18dp.png?raw=true) [Systematization TextField and States](https://keygenqt.github.io/compose-forms/systematizationTextField)
You can group fields into a form. You can make fields both custom and use ready-made

### ![picture](https://github.com/google/material-design-icons/blob/master/png/action/check_circle/materialicons/18dp/1x/baseline_check_circle_black_18dp.png?raw=true) DotsNumbers
Component with dots for code entry

### Preview
<p>
<img src="https://github.com/surfstudio/surf-compose-forms/blob/master/data/vokoscreen-2021-09-14_20-10-40.gif?raw=true" width="33%"/>
</p>

## License

```
Copyright 2021 Surf LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```