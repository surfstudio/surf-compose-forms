buildscript {

    val kotlinVersion: String by project
    val gradleVersion: String by project

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
    id("com.diffplug.spotless")
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(file("${project.rootDir}/spotless/LicenseHeader"))
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}