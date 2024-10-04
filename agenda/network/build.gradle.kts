plugins {
    alias(libs.plugins.tasky.android.library)
}

android {
    namespace = "com.tasky.agenda.network"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
