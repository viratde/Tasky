package com.tasky.test_utils.auth.domain

import com.tasky.auth.domain.PatternValidator

class FakePatternValidator : PatternValidator {
    var shouldPatternMatch = true

    override fun matches(value: String): Boolean {
        return shouldPatternMatch
    }
}
