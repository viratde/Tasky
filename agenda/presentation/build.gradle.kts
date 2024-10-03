plugins {
    alias(libs.plugins.tasky.android.library.compose.ui)
}

android {
    namespace = "com.tasky.agenda.presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
