buildscript {

    val kotlinVersion: String = findProperty("kotlinVersion") as? String ?: "1.6.10"

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
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