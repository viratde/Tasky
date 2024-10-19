plugins {
    `kotlin-dsl`
}

group = "com.tasky.buildLogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
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
        register("androidRoomConventionPlugin"){
            id = "tasky.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}

dependencies {

    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)

}