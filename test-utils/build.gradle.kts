plugins {
    alias(libs.plugins.tasky.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
    implementation(projects.agenda.domain)
    implementation(libs.junit)
    implementation(libs.junit.jupiter.api)
    implementation(libs.kotlinx.coroutines.test)
}
