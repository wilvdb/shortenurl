package com.github.urlshorten.junit5

import com.github.urlshorten.UrlRepository
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

@Tag("db")
@DataJpaTest
class GUrlRepositoryTest {

    @Autowired
    UrlRepository repository

    @Test
    void "should find by shortern url"() {
        // given
        def shortenUrl = "http://google.com/1234"

        // when
        def url = repository.findByShortenUrl(shortenUrl)

        // then
        assertTrue(url.isPresent())
        assertEquals(shortenUrl, url.get().shortenUrl)
    }
}
