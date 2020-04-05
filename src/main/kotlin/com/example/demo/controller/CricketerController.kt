package com.example.demo.controller

import arrow.core.Either
import arrow.core.None
import arrow.core.Some
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import com.example.demo.service.CricketerService
import com.example.demo.repository.CricketerRepository
import com.example.demo.model.Cricketer
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
class CricketerController(private val cricketerService: CricketerService , private val cricketerRepository: CricketerRepository){
	@GetMapping("/cricketers/{id}")
	suspend fun getCricketer(@PathVariable("id") id: Long):ResponseEntity<Cricketer>  {
		return when(val cricketer = cricketerService.findById(id).attempt().unsafeRunSync()) {
			is Either.Left ->  throw ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch cricketer")
			is Either.Right-> {
				when(cricketer.b) {
					is Some -> ResponseEntity(cricketer.b.orNull(), HttpStatus.OK)
					is None -> throw ResponseStatusException(
							HttpStatus.NOT_FOUND, "Cricketer with id $id not found")
				}
			}
		}
	}
	
	@GetMapping("/cricketers")
	suspend fun getAllCricketers() :ResponseEntity<List<Cricketer>>  {
		return when(val cricketers = cricketerService.getAllPlayers().attempt().unsafeRunSync()) {
			is Either.Left -> throw ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch cricketers")
			is Either.Right-> {
				return ResponseEntity(cricketers.b, HttpStatus.OK)
			}
		}
	}
	
	@PostMapping("/cricketer")
	suspend fun addCricketer(@RequestBody cricketer:Cricketer):ResponseEntity<Cricketer>  {
		when(val cricketer = cricketerService.save(cricketer).attempt().unsafeRunSync()) {
			is Either.Left -> throw ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save cricketer")
			is Either.Right-> {
				return ResponseEntity(cricketer.b , HttpStatus.OK)
			}
		}
	}
	
	@PutMapping("/cricketer/{id}")
	suspend fun updateCricketer(@PathVariable("id") id: Long, @RequestBody cricketer: Cricketer ):ResponseEntity<Cricketer> {
		val updatedCricketer  = Cricketer(name = cricketer.name
				, country = cricketer.country
				, highestScore = cricketer.highestScore)
		when(val cricketer = cricketerService.save(updatedCricketer).attempt().unsafeRunSync()) {
			is Either.Left -> throw ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update cricketer $id")
			is Either.Right-> {
				return ResponseEntity(cricketer.b , HttpStatus.OK)
			}
		}
	}
	
	@DeleteMapping("/cricketer/{id}")
	suspend fun deleteCricketer(@PathVariable("id") id:Long ):ResponseEntity<String> {
		when(cricketerService.deleteById(id).attempt().unsafeRunSync()) {
			is Either.Left -> throw ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete cricketer $id")
			is Either.Right-> {
				return ResponseEntity(HttpStatus.OK)
			}
		}
	}
}