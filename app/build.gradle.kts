plugins {
    alias(libs.plugins.tasky.android.application.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.tasky"

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)


    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.espresso.core)

    //crypto
    implementation(libs.androidx.security.crypto.ktx)

    //
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //koin
    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.compose)

    //projects
    implementation(projects.auth.presentation)
    implementation(projects.core.presentation.designSystem)
    implementation(projects.core.data)
    implementation(projects.auth.data)
    implementation(projects.agenda.presentation)

    //splash screen
    implementation(libs.androidx.core.splashscreen)


}