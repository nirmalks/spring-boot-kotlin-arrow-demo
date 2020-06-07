package com.example.demo.model
import arrow.core.Nel
import arrow.core.Validated
import arrow.core.extensions.list.traverse.traverse
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicative.applicative
import arrow.core.fix
import javax.persistence.*

@Entity
data class Cricketer(@Id @GeneratedValue
                     val id:Long? = null,
                     val name: String,
                     @Enumerated(EnumType.STRING)
                     val country: Country,
                     val highestScore: Number ) {
    fun validate(): Validated<Nel<ValidationError>, Cricketer> {
        val validation = Rules accumulateErrors {
            listOf(validateName())
        }
        return validation.map {
            it.fix()
        }.traverse(Validated.applicative(Nel.semigroup<ValidationError>())) { it }
                .fix()
                .map {
                    it.fix().first()
                }
    }
}