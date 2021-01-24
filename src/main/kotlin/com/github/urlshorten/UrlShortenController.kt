package com.github.urlshorten

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@Validated
class UrlShortenController(val service: UrlShortenService) {

    @GetMapping("/url_shorten/{url}")
    fun retrieve(@PathVariable("url") url: String): ResponseEntity<ShortenedUrl> {
        val retrievedUrl = service.retrieve(url)
        return if (retrievedUrl == null) {
            ResponseEntity<ShortenedUrl>(null, HttpStatus.NOT_FOUND)
        } else ResponseEntity.ok(retrievedUrl.let {
            ShortenedUrl(
                    shortenUrl = it.shortenUrl ?: "",
                    originalUrl = it.originalUrl,
                    creationDate = it.creationDate,
                    expirationDate = it.expirationDate,
            )
        })
    }

    @PostMapping("/url_shorten")
    fun shorten(@RequestBody url: UrlToShorten) = ResponseEntity.created(URI.create(service.shorten(url.url, IdStrategy.valueOf(url.strategy.name))?.shortenUrl)).build<URI>()
}