plugins {
    alias(libs.plugins.tasky.android.library)
}

android {
    namespace = "com.tasky.agenda.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(projects.core.domain)
    implementation(projects.agenda.domain)
    implementation(projects.core.data)
}
