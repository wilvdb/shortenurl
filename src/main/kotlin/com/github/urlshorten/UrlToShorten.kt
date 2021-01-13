package com.github.urlshorten

data class UrlToShorten(
        val url: String,
        val strategy: GeneratorStrategy
) {

    enum class GeneratorStrategy {
        BASE_10, BASE_62
    }
}