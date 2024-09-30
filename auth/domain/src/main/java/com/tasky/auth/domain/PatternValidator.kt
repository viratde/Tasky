package com.tasky.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}