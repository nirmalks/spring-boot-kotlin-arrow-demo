package com.example.demo.service

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.example.demo.model.Venue
import com.example.demo.repository.VenueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VenueService(@Autowired val venueRepository: VenueRepository) {
    suspend fun save(venue: Venue): IO<Venue> {
        return IO.fx {
            venueRepository.save(venue)
        }
    }
}