package com.tasky.auth.presentation.login

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.tasky.test_utils.auth.domain.FakeAuthRepository
import com.tasky.test_utils.auth.domain.FakePatternValidator
import com.tasky.core.domain.util.DataError
import com.tasky.core.presentation.ui.asUiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginViewModelTest {


    private lateinit var loginViewModel: LoginViewModel
    private lateinit var authRepository: FakeAuthRepository
    private lateinit var patternValidator: FakePatternValidator
    private lateinit var testDispatcher: TestDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        patternValidator = FakePatternValidator()
        authRepository = FakeAuthRepository()
        loginViewModel = LoginViewModel(
            patternValidator = patternValidator,
            authRepository = authRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Email Changes Reflect, And Also Matches Email With Pattern Validator`() {

        patternValidator.shouldPatternMatch = true
        loginViewModel.onAction(LoginAction.OnEmailChange("test@gmail.com"))
        assertThat(loginViewModel.state.email).isEqualTo("test@gmail.com")
        assertThat(loginViewModel.state.isValidEmail).isTrue()

        patternValidator.shouldPatternMatch = false
        loginViewModel.onAction(LoginAction.OnEmailChange("test@test"))
        assertThat(loginViewModel.state.email).isEqualTo("test@test")
        assertThat(loginViewModel.state.isValidEmail).isFalse()

    }

    @Test
    fun `Password Changes Reflect Correctly`() {

        loginViewModel.onAction(LoginAction.OnPasswordChange("1234@test"))
        assertThat(loginViewModel.state.password).isEqualTo("1234@test")

    }

    @Test
    fun `Toggle Password Visibility Is Correctly Working`() {

        val visibility = loginViewModel.state.isPasswordVisible

        loginViewModel.onAction(LoginAction.OnTogglePasswordVisibility)
        assertThat(loginViewModel.state.isPasswordVisible).isEqualTo(!visibility)

        loginViewModel.onAction(LoginAction.OnTogglePasswordVisibility)
        assertThat(loginViewModel.state.isPasswordVisible).isEqualTo(visibility)

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Login Event Loading Works Correctly`() =
        runTest(testDispatcher) {

            assertThat(loginViewModel.state.isLoggingIn).isFalse()
            loginViewModel.onAction(LoginAction.OnLogin)

            advanceTimeBy(100L)
            assertThat(loginViewModel.state.isLoggingIn).isTrue()
            advanceUntilIdle()
            assertThat(loginViewModel.state.isLoggingIn).isFalse()

        }

    @Test
    fun `Login Event Handles Error And Success Event`() = runTest {
        loginViewModel.events.test {

            authRepository.errorToReturn = null
            loginViewModel.onAction(LoginAction.OnLogin)

            val event1 = awaitItem()

            assertThat(event1).isEqualTo(LoginEvent.OnLoginSuccess)

            authRepository.errorToReturn = DataError.Network.SERVER_ERROR
            loginViewModel.onAction(LoginAction.OnLogin)
            val event2 = awaitItem()

            assertThat(event2).isEqualTo(LoginEvent.OnError(DataError.Network.SERVER_ERROR.asUiText()))

        }
    }


}