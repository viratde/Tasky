package com.tasky.common

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class InstrumentationTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TaskyTestApp::class.java.name, context)
    }

}