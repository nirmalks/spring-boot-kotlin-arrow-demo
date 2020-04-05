package com.example.demo.service
import arrow.core.Option
import arrow.core.toOption
import arrow.fx.IO
import arrow.fx.extensions.fx
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import com.example.demo.repository.CricketerRepository
import com.example.demo.model.Cricketer
import java.util.*

@Service
class CricketerService(@Autowired val cricketerRepository:CricketerRepository) {
	suspend fun save(cricketer: Cricketer ): IO<Cricketer> {
		return IO {
			cricketerRepository.save(cricketer)
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
}
