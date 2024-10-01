plugins {
    alias(libs.plugins.tasky.android.library.compose.ui)
}

android {
    namespace = "com.tasky.auth.presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)

}