plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
    id("com.jfrog.artifactory")
}

// dependencies versions
val composeVersion: String by project
val googleAccompanistVersion: String by project

// lib info
val libVersion: String by project
val libGroup: String by project

publishing {
    publications {
        register("aar", MavenPublication::class) {
            version = libVersion
            groupId = libGroup
            artifactId = project.name
            artifact("$buildDir/outputs/aar/compose-forms-$libVersion-release.aar")
        }
    }
}

artifactory {
    setContextUrl("https://artifactory.surfstudio.ru/artifactory")
    publish {
        repository {
            setRepoKey("libs-release-local")
            setUsername(System.getenv("surf_maven_username"))
            setPassword(System.getenv("surf_maven_password"))
        }
        defaults {
            publications("aar")
            setPublishArtifacts(true)
        }
    }
}

android {

    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31
        setProperty("archivesBaseName", "compose-forms-$libVersion")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("com.google.accompanist:accompanist-insets:$googleAccompanistVersion")
    implementation("com.google.accompanist:accompanist-insets-ui:$googleAccompanistVersion")
    implementation("androidx.compose.material3:material3:1.0.0-alpha02")
    implementation("ru.surfstudio.compose:modifier-ext:0.0.12")

    testImplementation("junit:junit:4.13.2")
}
