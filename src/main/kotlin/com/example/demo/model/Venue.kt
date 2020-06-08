package com.example.demo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Venue(@Id @GeneratedValue val id:Long? = null,val country: Country, val name: String) {}