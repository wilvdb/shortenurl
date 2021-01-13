package com.github.urlshorten

import java.time.LocalDateTime

data class ShortenedUrl(
        val shortenUrl: String,
        val originalUrl: String,
        val creationDate: LocalDateTime,
        val expirationDate: LocalDateTime) {
}