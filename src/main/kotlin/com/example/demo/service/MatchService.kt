package com.example.demo.service

import arrow.core.*
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.IO.Companion.effect
import arrow.fx.extensions.io.monad.monad
import arrow.fx.fix
import arrow.fx.handleError
import arrow.mtl.EitherT
import arrow.mtl.extensions.eithert.monad.monad
import arrow.mtl.value
import com.example.demo.model.*
import com.example.demo.repository.MatchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MatchService(@Autowired val matchRepository: MatchRepository,
        @Autowired val venueService: VenueService) {
    suspend fun save(match: Match): IO<Either<OperationError,Match>> {
        return EitherT.monad<OperationError, ForIO>(IO.monad()).fx.monad {
            val venue = !EitherT(effect {venueService.save(Venue(Country.INDIA,"chennai")).right()}
                            .handleError { OperationError.DBError("Something went wrong").left()})
            matchRepository.save(match)
        }.value().fix()
    }

    suspend fun findById(id:Long): IO<Option<Match>> {
        return IO {
            matchRepository.findById(id).get().toOption()
        }
    }

    suspend fun getAllMatches(): IO<List<Match>> {
        return IO {
            matchRepository.findAll()
        }
    }

    suspend fun deleteById(id: Long): IO<Unit> {
        return IO {
            matchRepository.deleteById(id)
        }
    }
}