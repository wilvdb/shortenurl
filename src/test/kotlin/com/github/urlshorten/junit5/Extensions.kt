package com.github.urlshorten.junit5

import com.github.urlshorten.Url

fun Url?.shortenUrlContains(string: String) = (this?.shortenUrl ?: "").contains(string)

fun Url?.shortenUrlEquals(str: String) = this?.shortenUrl.equals(str)