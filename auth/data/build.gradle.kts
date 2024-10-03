plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.ktor)
}

android {
    namespace = "com.tasky.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(libs.bundles.koin)
}
