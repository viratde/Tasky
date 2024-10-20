plugins {
    alias(libs.plugins.tasky.android.library)
    alias(libs.plugins.tasky.jvm.ktor)
}

android {
    namespace = "com.tasky.agenda.network"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.agenda.domain)
    testImplementation(libs.ktor.client.mock)

}
