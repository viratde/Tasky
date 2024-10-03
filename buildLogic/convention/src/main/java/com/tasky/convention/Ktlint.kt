package com.tasky.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType


fun Project.configureKtlint() {

    extensions.configure<KtlintExtension> {
        android.set(true)
        ignoreFailures.set(false)
        reporters {

            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
            reporter(ReporterType.SARIF)

        }
    }

}
