package com.example.demo.repository

import com.example.demo.model.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository: JpaRepository<Match, Long> {
}