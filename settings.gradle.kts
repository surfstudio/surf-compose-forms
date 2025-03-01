pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        // https://www.jfrog.com/confluence/display/JFROG/Gradle+Artifactory+Plugin
        id("com.jfrog.artifactory") version "4.24.16"
        // https://github.com/diffplug/spotless
        id("com.diffplug.spotless") version "5.12.5"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://artifactory.surfstudio.ru/artifactory/libs-release-local")
    }
}

rootProject.name = "surf-compose-forms"
include(":forms")
include(":sample")
