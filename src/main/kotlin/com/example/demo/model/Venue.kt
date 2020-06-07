package com.example.demo.model

import javax.persistence.Entity

@Entity
data class Venue(val country: Country, val name: String) {}