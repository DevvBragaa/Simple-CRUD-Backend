package com.dev.crud.exception

import java.time.LocalDateTime

data class ErrorView (
    val timeStamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String ?,
    val path: String
) {
}