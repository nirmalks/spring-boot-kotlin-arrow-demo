package com.example.demo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Match(
        @Id @GeneratedValue
        val id: Long? = null,
        @OneToMany
        val teams: List<Team>,
        val summary: String,
        val winner: String,
        val venue: Venue) {
        companion object {
                fun toDomain() {

                }
        }
}

data class MatchRequest(
        val id:Long? = null,
        val teams: List<Team>,
        val summary: String,
        val winner: String = ""
)