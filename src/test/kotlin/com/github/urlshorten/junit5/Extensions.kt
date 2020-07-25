package com.github.urlshorten.junit5

import com.github.urlshorten.Url

fun Url.shortenUrlContains(string: String): Boolean {
    return (this.shortenUrl ?: "").contains(string)
}