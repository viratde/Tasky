package com.tasky.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope


fun DependencyHandlerScope.addAndroidTestDependency(project: Project) {
    addJvmTestDependency(project)
    "androidTestImplementation"(project.libs.findLibrary("androidx.junit").get())
}


fun DependencyHandlerScope.addJvmTestDependency(project: Project) {

    "testImplementation"(project.libs.findLibrary("junit").get())
    "testImplementation"(project.libs.findLibrary("junit.jupiter.api").get())
    "testRuntimeOnly"(project.libs.findLibrary("junit.jupiter.engine").get())
    "testImplementation"(project.libs.findLibrary("junit.jupiter.params").get())
    "testImplementation"(project.libs.findLibrary("turbine").get())
    "testImplementation"(project.libs.findLibrary("kotlinx.coroutines.test").get())
    "testImplementation"(project.libs.findLibrary("assertk").get())

}