package com.example.demo.model

import javax.persistence.*

@Entity
data class Team(
        @Id @GeneratedValue
        val id:Long? = null,
        @OneToMany
        val cricketers: List<Cricketer>
) {
}