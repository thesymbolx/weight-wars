import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "uk.co.weightwars.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

gradlePlugin {
    plugins {
        create("androidApplication") {
            id = "co.uk.weightwars.convention.application"
            implementationClass = "uk.co.weightwars.convention.AndroidApplicationConventionPlugin"
        }
        create("androidLibrary") {
            id = "uk.co.weightwars.convention.library"
            implementationClass = "uk.co.weightwars.convention.AndroidLibraryConventionPlugin"
        }
        create("hilt") {
            id = "co.uk.weightwars.convention.hilt"
            implementationClass = "uk.co.weightwars.convention.HiltConventionPlugin"
        }

    }
}