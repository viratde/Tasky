plugins {
    alias(libs.plugins.tasky.android.library.compose)
}

android {
    namespace = "com.tasky.core.presentation.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.material3)

}
