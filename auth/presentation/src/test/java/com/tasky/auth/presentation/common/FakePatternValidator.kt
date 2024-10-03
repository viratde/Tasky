package com.tasky.auth.presentation.common

import com.tasky.auth.domain.PatternValidator


class FakePatternValidator : PatternValidator {
    var shouldReturn = true
    override fun matches(value: String): Boolean {
        return shouldReturn
    }
}