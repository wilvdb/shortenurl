package com.github.urlshorten.junit5

import com.github.urlshorten.UrlRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@Tag("db")
@DataJpaTest
open class KUrlRepositioryTest(@Autowired val repository: UrlRepository) {

    @Test
    fun `should find by shortern url`() {
        // given
        val shortenUrl = "http://google.com/1234"

        // when
        val url = repository.findByShortenUrl(shortenUrl)

        // then
        assertTrue(url.isPresent)
        assertEquals(shortenUrl, url.get().shortenUrl)
    }
}