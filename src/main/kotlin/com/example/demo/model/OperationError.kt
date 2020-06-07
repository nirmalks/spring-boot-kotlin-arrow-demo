package com.example.demo.model

sealed class OperationError(val msg: String) {
    data class CommonError(val commonErr: String) : OperationError(commonErr)
    data class DBError(val dbErr: String) : OperationError(dbErr)
}