plugins {
    alias(libs.plugins.tasky.android.library.compose)
}

android {
    namespace = "com.tasky.core.presentation.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(projects.core.domain)
}
