plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.google.gms.google.services)
//    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("co.uk.weightwars.convention.application")

}

android {
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    //Modules
    implementation(project(":feature:challenges"))
    implementation(project(":feature:overview"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:friends"))
    implementation(project(":core:network"))



    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.navigation.common.ktx)
    implementation(libs.navigation.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(libs.compose.navigation)
    implementation(libs.kotlinx.serialization.core)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.firebase.database)
    debugImplementation(libs.androidx.ui.test.manifest)
}