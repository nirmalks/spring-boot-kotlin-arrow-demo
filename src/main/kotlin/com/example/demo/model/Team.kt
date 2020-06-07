package com.example.demo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Team(
        @Id @GeneratedValue
        val id:Long? = null,
        @OneToMany
        val cricketers: List<Cricketer>
) {
}