package com.tasky.auth.presentation.signup

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.tasky.auth.domain.UserDataValidator
import com.tasky.core.domain.util.DataError
import com.tasky.core.presentation.ui.asUiText
import com.tasky.test_utils.auth.domain.FakeAuthRepository
import com.tasky.test_utils.auth.domain.FakePatternValidator
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SignUpViewModelTest {


    private lateinit var userDataValidator: UserDataValidator
    private lateinit var patternValidator: FakePatternValidator
    private lateinit var authRepository: FakeAuthRepository
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var viewModel: SignUpViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {

        patternValidator = FakePatternValidator()
        userDataValidator = UserDataValidator(
            patternValidator = patternValidator
        )
        authRepository = FakeAuthRepository()
        viewModel = SignUpViewModel(
            userDataValidator, authRepository
        )
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Email Change Reflects Correctly And Calls PatternMatcher Matches Function`() {

        patternValidator.shouldPatternMatch = true
        viewModel.onAction(SignUpAction.OnEmailChange("test@gmail.com"))
        assertThat(viewModel.state.email).isEqualTo("test@gmail.com")
        assertThat(viewModel.state.isValidEmail).isTrue()

        patternValidator.shouldPatternMatch = false
        viewModel.onAction(SignUpAction.OnEmailChange("test@test"))
        assertThat(viewModel.state.email).isEqualTo("test@test")
        assertThat(viewModel.state.isValidEmail).isFalse()

    }

    @ParameterizedTest
    @CsvSource(
        "Virat,true",
        "Vir,false",
        "ph,false",
        "Philipp,true"
    )
    fun `Name Change Reflects, And Validates it correctly`(
        name: String,
        isValid: Boolean
    ) {
        viewModel.onAction(SignUpAction.OnNameChange(name))
        assertThat(viewModel.state.name).isEqualTo(name)
        assertThat(viewModel.state.isValidName).isEqualTo(isValid)
    }

    @ParameterizedTest
    @CsvSource(
        "Virat,false",
        "1234@Virat,true",
        "71080,false",
        "Philipp@1233,true"
    )
    fun `Password Change Reflects, And Validates it correctly`(
        password: String,
        isValid: Boolean
    ) {
        viewModel.onAction(SignUpAction.OnPasswordChange(password))
        assertThat(viewModel.state.password).isEqualTo(password)
        assertThat(viewModel.state.isValidPassword).isEqualTo(isValid)
    }

    @Test
    fun `Toggle Password Visibility Is Correctly Working`() {

        val visibility = viewModel.state.isPasswordVisible

        viewModel.onAction(SignUpAction.OnTogglePasswordVisibility)
        assertThat(viewModel.state.isPasswordVisible).isEqualTo(!visibility)

        viewModel.onAction(SignUpAction.OnTogglePasswordVisibility)
        assertThat(viewModel.state.isPasswordVisible).isEqualTo(visibility)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Signup Event Loading Works Correctly`() =
        runTest(testDispatcher) {

            assertThat(viewModel.state.isSigningUp).isFalse()
            viewModel.onAction(SignUpAction.OnSignUp)

            advanceTimeBy(100L)
            assertThat(viewModel.state.isSigningUp).isTrue()
            advanceUntilIdle()
            assertThat(viewModel.state.isSigningUp).isFalse()

        }

    @Test
    fun `SignUp Event Handles Error And Success Event`() = runTest {
        viewModel.events.test {

            authRepository.errorToReturn = null
            viewModel.onAction(SignUpAction.OnSignUp)

            val event1 = awaitItem()

            assertThat(event1).isEqualTo(SignUpEvent.OnSignUpSuccess)

            authRepository.errorToReturn = DataError.Network.SERVER_ERROR
            viewModel.onAction(SignUpAction.OnSignUp)
            val event2 = awaitItem()

            assertThat(event2).isEqualTo(SignUpEvent.OnError(DataError.Network.SERVER_ERROR.asUiText()))

        }
    }

}