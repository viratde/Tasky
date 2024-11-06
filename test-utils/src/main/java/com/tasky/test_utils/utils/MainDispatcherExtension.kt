package com.tasky.test_utils.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class MainDispatcherExtension : BeforeEachCallback, AfterEachCallback {

    private lateinit var testDispatcher: TestDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun beforeEach(context: ExtensionContext?) {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}