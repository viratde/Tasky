package com.tasky.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(value: String): Boolean = patternValidator.matches(value)


    fun isValidPassword(value: String): Boolean {
        return value.length >= MIN_PASSWORD_LENGTH &&
                value.any { it.isDigit() } &&
                value.any { it.isUpperCase() } &&
                value.any { it.isLowerCase() }

    }

    fun isValidFullName(value: String): Boolean {
        return value.length in MIN_FULL_NAME_LENGTH..MAX_FULL_NAME_LENGTH
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
        const val MIN_FULL_NAME_LENGTH = 5
        const val MAX_FULL_NAME_LENGTH = 49
    }

}