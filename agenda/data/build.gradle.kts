plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.android.room)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.tasky.agenda.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.agenda.domain)
    implementation(projects.core.data)
}
