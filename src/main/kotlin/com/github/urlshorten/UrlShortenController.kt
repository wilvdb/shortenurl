package com.github.urlshorten

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@Validated
class UrlShortenController(val service: UrlShortenService, val mapper: Mapper) {

    @GetMapping("/url_shorten/{url}")
    fun retrieve(@PathVariable("url") url: String?) = ResponseEntity.of(service.retrieve(url).map { mapper.toDto(it) })

    @GetMapping("/url_shorten")
    fun shorten(@RequestBody url: UrlToShorten) = ResponseEntity.created(URI.create(service.shorten(url.url, mapper.toIdStrategy(url.strategy)).shortenUrl)).build<URI>()
}