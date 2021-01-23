package com.github.urlshorten

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
open class UrlShortenApplication

fun main(args: Array<String>) {
    runApplication<UrlShortenApplication>(*args)
}