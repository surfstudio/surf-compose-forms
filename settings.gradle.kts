pluginManagement {
    val artifactoryVersions: String by settings
    val spotlessVersions: String by settings

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        // https://www.jfrog.com/confluence/display/JFROG/Gradle+Artifactory+Plugin
        id("com.jfrog.artifactory") version artifactoryVersions
        // https://github.com/diffplug/spotless
        id("com.diffplug.spotless") version spotlessVersions
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
