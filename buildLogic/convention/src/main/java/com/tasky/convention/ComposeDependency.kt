package com.tasky.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addUiLayerDependencies(project: Project) {
    "implementation"(project(":core:presentation:designSystem"))
    "implementation"(project(":core:presentation:ui"))

    "implementation"(project.libs.findLibrary("androidx.runtime.tracing").get())
    "implementation"(project.libs.findBundle("compose").get())
    "debugImplementation"(project.libs.findBundle("compose.debug").get())
    "androidTestImplementation"(project.libs.findLibrary("androidx.ui.test.junit4").get())

    "implementation"(project.libs.findBundle("koin.compose").get())
}