package com.example.demo.model

import javax.persistence.*

@Entity
@Table(name="matches")
data class Match(
        @Id @GeneratedValue
        val id: Long? = null,
        @OneToMany
        val teams: List<Team>,
        val summary: String,
        val winner: String) {
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
) {}