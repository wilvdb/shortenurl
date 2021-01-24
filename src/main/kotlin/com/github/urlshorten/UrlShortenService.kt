package com.github.urlshorten

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.MalformedURLException
import java.net.URL
import java.time.LocalDateTime

@Service
class UrlShortenService(val urlRepository: UrlRepository) {

    @Transactional
    open fun shorten(url: String, strategy: IdStrategy): Url? {
        var urlForShorten = Url(
                id = null,
                creationDate = LocalDateTime.now(),
                expirationDate = LocalDateTime.now().plusYears(2L),
                originalUrl = url,
                shortenUrl = null,
        )
        urlForShorten = urlRepository.save(urlForShorten)
        val id = strategy.apply(urlForShorten.id!!)
        try {
            val netUrl = URL(url)
            urlForShorten.shortenUrl = url.replace(netUrl.path, "/$id")
        } catch (e: MalformedURLException) {
            throw IllegalArgumentException(e)
        }
        return urlForShorten
    }

    @Transactional(readOnly = true)
    open fun retrieve(shortenUrl: String): Url? = urlRepository.findByShortenUrl(shortenUrl)
}