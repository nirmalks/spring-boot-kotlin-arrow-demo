package com.example.demo.model

sealed class ValidationError(val msg: String) {
    data class NotAValidCountry(val value: String) : ValidationError("$value did not belong to valid country")
    data class MaxLengthName(val value: String) : ValidationError("Exceeded length of $value")
    data class MaxLength(val value: Int) : ValidationError("Exceeded length of $value")
}