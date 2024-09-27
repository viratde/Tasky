package com.tasky.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {

    commonExtension.run {

        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            "debugImplementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
            "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
            "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())
            "androidTestImplementation"(libs.findLibrary("androidx.ui-test-junit4").get())
        }
    }

}