package com.github.urlshorten.junit5

import com.github.urlshorten.Url
import java.util.*

fun Url.shortenUrlContains(string: String) = (this.shortenUrl ?: "").contains(string)

fun Optional<Url>.shortenUrlEquals(str: String) = this.get()?.shortenUrl.equals(str)