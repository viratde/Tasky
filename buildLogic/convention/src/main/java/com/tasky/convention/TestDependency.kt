package com.tasky.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project


fun DependencyHandlerScope.addAndroidTestDependency(project: Project) {
    "androidTestImplementation"(project.libs.findLibrary("androidx.runner").get())
    "androidTestImplementation"(project.libs.findLibrary("androidx.junit").get())
    "androidTestImplementation"(project.libs.findLibrary("kotlinx.coroutines.test").get())
    "androidTestImplementation"(project.libs.findLibrary("assertk").get())
    "androidTestImplementation"(project.libs.findLibrary("turbine").get())
    "androidTestImplementation"(project(":test-utils"))
    addJvmTestDependency(project)
}


fun DependencyHandlerScope.addJvmTestDependency(project: Project) {

    "testImplementation"(project.libs.findLibrary("junit").get())
    "testImplementation"(project.libs.findLibrary("junit.jupiter.api").get())
    "testRuntimeOnly"(project.libs.findLibrary("junit.jupiter.engine").get())
    "testImplementation"(project.libs.findLibrary("junit.jupiter.params").get())
    "testImplementation"(project.libs.findLibrary("turbine").get())
    "testImplementation"(project.libs.findLibrary("kotlinx.coroutines.test").get())
    "testImplementation"(project.libs.findLibrary("assertk").get())
    "testImplementation"(project(":test-utils"))

}