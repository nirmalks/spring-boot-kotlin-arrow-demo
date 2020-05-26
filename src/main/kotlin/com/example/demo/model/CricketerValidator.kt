package com.example.demo.model

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.typeclasses.ApplicativeError

sealed class Rules<F>(A: ApplicativeError<F, Nel<ValidationError>>)
    : ApplicativeError<F, Nel<ValidationError>> by A {
    fun Cricketer.validateName(): Kind<F, Cricketer> {
        return if (this.name.length > 25) {
            raiseError(ValidationError.MaxLengthName(this.name).nel())
        } else {
            just(this)
        }
    }

    object ErrorAccumulationStrategy :
            Rules<ValidatedPartialOf<Nel<ValidationError>>>(Validated.applicativeError(NonEmptyList.semigroup()))

    companion object {
        infix fun <A> accumulateErrors(f: ErrorAccumulationStrategy.() -> A): A =
                f(ErrorAccumulationStrategy)
    }
}
