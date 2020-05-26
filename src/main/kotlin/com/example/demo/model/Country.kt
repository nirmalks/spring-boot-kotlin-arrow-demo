package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class Country(val country: String) {
    @JsonProperty("ind")
    INDIA("ind"),
    @JsonProperty("sa")
    SOUTH_AFRICA("sa"),
    @JsonProperty("aus")
    AUSTRALIA("aus"),
    @JsonProperty("pak")
    PAKISTAN("pak"),
    @JsonProperty("emg")
    ENGLAND("eng"),
    @JsonProperty("sl")
    SRI_LANKA("sl");

    companion object  {
        fun isValidCountry(countryName: String): Boolean {
            return values().any { it.country == countryName }
        }
    }
}