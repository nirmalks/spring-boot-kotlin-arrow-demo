package com.example.demo.controller

import arrow.core.Either
import arrow.core.None
import arrow.core.Some
import com.example.demo.model.Match
import com.example.demo.service.MatchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/match")
class MatchController(@Autowired val matchService: MatchService) {

    @GetMapping("/{id}")
    suspend fun getMatch(@PathVariable("id") id: Long): ResponseEntity<Match> {
        return when(val match = matchService.findById(id).attempt().unsafeRunSync()) {
            is Either.Left ->  throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch match", match.a)
            is Either.Right-> {
                when(match.b) {
                    is Some -> ResponseEntity(match.b.orNull(), HttpStatus.OK)
                    is None -> throw ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Match with id $id not found")
                }
            }
        }
    }

    @GetMapping("/")
    suspend fun getAllMatches() : ResponseEntity<List<Match>> {
        return when(val matches = matchService.getAllMatches().attempt().unsafeRunSync()) {
            is Either.Left -> throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch matches")
            is Either.Right-> {
                return ResponseEntity(matches.b, HttpStatus.OK)
            }
        }
    }

    @PostMapping("/")
    suspend fun addMatch(@RequestBody match: Match): ResponseEntity<Match> {
        when (val validatedMatch = matchService.save(match).attempt().unsafeRunSync()) {
            is Either.Left -> {
                throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Unable to add match ${validatedMatch.a}")
            }
            is Either.Right -> {
                when (val savedMatch = validatedMatch.b) {
                    is Either.Left ->
                        throw ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR, "Unable to add match ${savedMatch.a}")
                    is Either.Right -> return ResponseEntity(savedMatch.b, HttpStatus.OK)
                }
            }
        }
    }

    @PutMapping("/{id}")
    suspend fun updateMatch(@PathVariable("id") id: Long, @RequestBody match: Match): ResponseEntity<Match> {
        when (val match = matchService.save(match).attempt().unsafeRunSync()) {
            is Either.Left -> { throw ResponseStatusException(HttpStatus.BAD_REQUEST, match.a.localizedMessage) }
            is Either.Right -> {
                when (val savedMatch = match.b) {
                    is Either.Left ->
                        throw ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update match $id")
                    is Either.Right -> return ResponseEntity(savedMatch.b, HttpStatus.OK)
                }
            }
        }
    }

    @DeleteMapping("/{id}")
    suspend fun deleteMatch(@PathVariable("id") id:Long ): ResponseEntity<String> {
        when(matchService.deleteById(id).attempt().unsafeRunSync()) {
            is Either.Left -> throw ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete match $id")
            is Either.Right-> {
                return ResponseEntity(HttpStatus.OK)
            }
        }
    }
}