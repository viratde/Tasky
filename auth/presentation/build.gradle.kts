plugins {
    alias(libs.plugins.tasky.android.library.compose.ui)
}

android {
    namespace = "com.tasky.auth.presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}
