plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.ktor)
}

android {
    namespace = "com.tasky.auth.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.auth.domain)
    implementation(projects.core.data)
    implementation(projects.core.domain)
}