package com.example.demo.service
import arrow.core.*
import arrow.fx.IO
import arrow.fx.IO.Companion.effect
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.concurrent.parTraverse
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import com.example.demo.repository.CricketerRepository
import com.example.demo.model.Cricketer

@Service
class CricketerService(@Autowired val cricketerRepository:CricketerRepository) {
	suspend fun save(cricketer: Cricketer): IO<Either<String,Cricketer>> {
		return IO.fx {
			when (val validatedCricketer = cricketer.validate()) {
				is Validated.Valid -> cricketerRepository.save(validatedCricketer.a).right()
				is Validated.Invalid -> validatedCricketer.e.all.joinToString().left()
			}
		}
	}

	suspend fun findById(id:Long): IO<Option<Cricketer>> {
		return IO {
			cricketerRepository.findById(id).get().toOption()
		}
	}

	suspend fun getAllPlayers(): IO<List<Cricketer>> {
		return IO {
			cricketerRepository.findAll()
		}
	}

	suspend fun deleteById(id: Long): IO<Unit> {
		return IO {
			cricketerRepository.deleteById(id)
		}
	}

	suspend fun deleteMultipleCricketers(ids: Set<Long>): IO<List<Unit>> {
		return ids.parTraverse {
			effect { cricketerRepository.deleteById(it) }
		}
	}
}
