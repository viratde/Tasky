plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.ktor)
}

android {
    namespace = "com.tasky.core.data"
}

dependencies {

    implementation(projects.core.domain)
    implementation(libs.bundles.koin)
    implementation(libs.timber)
}
