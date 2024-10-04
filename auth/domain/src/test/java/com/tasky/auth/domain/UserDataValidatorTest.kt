package com.tasky.auth.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tasky.test_utils.auth.domain.FakePatternValidator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UserDataValidatorTest {
    private lateinit var userDataValidator: UserDataValidator
    private lateinit var patternValidator: FakePatternValidator

    @BeforeEach
    fun setUp() {
        patternValidator = FakePatternValidator()
        userDataValidator =
            UserDataValidator(
                patternValidator = patternValidator,
            )
    }

    @ParameterizedTest
    @CsvSource(
        // Valid passwords
        "ValidPass1,true", // Meets all criteria
        "Another9Test,true", // Meets all criteria
        // Invalid passwords
        "short1A,false", // Too short
        "nouppercase123,false", // No uppercase letter
        "NOLOWERCASE123,false", // No lowercase letter
        "NoDigitPassword,false", // No digit
        "123456789,false", // Only digits
        "PasswordPassword,false", // No digit
        "ALLUPPERCASE123,false", // No lowercase
        "alllowercase123,false", // No uppercase
        "Password,false", // Missing digit
    )
    fun `Validates Password Correctly`(
        password: String,
        isValid: Boolean,
    ) {
        assertThat(userDataValidator.isValidPassword(password)).isEqualTo(isValid)
    }

    @ParameterizedTest
    @CsvSource(
        // Valid full names (between 5 and 49 characters)
        "John Doe,true", // 8 characters
        "Jane,false", // Exactly 4 characters, too short
        "Alice Wonderland,true", // 16 characters
        "A true full name,true", // 15 characters
        "This name is exactly 49 chars long which is valid,true", // Exactly 49 characters
        // Invalid full names
        "A,false", // Too short, 1 character
        "AB,false", // Too short, 2 characters
        "Name with 50 characters which is too long for validation,false", // 50 characters
        "This name has 50 characters and it exceeds the max length,false", // Too long, 50 chars
    )
    fun `Validates Name Correctly`(
        name: String,
        isValid: Boolean,
    ) {
        assertThat(userDataValidator.isValidFullName(name)).isEqualTo(isValid)
    }
}
