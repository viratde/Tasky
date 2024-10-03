plugins {
    `kotlin-dsl`
}

group = "com.tasky.buildLogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    jvmToolchain(11)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "tasky.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "tasky.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "tasky.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidComposeLibrary") {
            id = "tasky.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidComposeUiLibrary") {
            id = "tasky.android.library.compose.ui"
            implementationClass = "AndroidComposeUiConventionPlugin"
        }
        register("JvmLibrary") {
            id = "tasky.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("JvmKtorLibrary") {
            id = "tasky.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
        register("Ktlint") {
            id = "tasky.ktlint"
            implementationClass = "KtlintConventionPlugin"
        }
    }
}

dependencies {

    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)

}