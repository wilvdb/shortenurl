package com.github.urlshorten

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Url(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,

        @Column(name = "original_url", length = 2083, nullable = false)
        var originalUrl: String,

        @Column(name = "shorten_url", length = 62)
        var shortenUrl: String?,

        @Column(name = "creation_date", nullable = false)
        var creationDate: LocalDateTime,

        @Column(name = "expiration_date", nullable = false)
        var expirationDate: LocalDateTime) {}