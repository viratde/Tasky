package com.tasky.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extension: Extension
) {


    commonExtension.run {

        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")

        when (extension) {
            Extension.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }

            Extension.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }
        }

    }
}

fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String
) {
    isMinifyEnabled = false
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://tasky.pl-coding.com\"")
}

fun BuildType.configureDebugBuildType(
    apiKey: String
) {

    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://tasky.pl-coding.com\"")

}