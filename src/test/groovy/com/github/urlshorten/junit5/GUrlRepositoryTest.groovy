package com.github.urlshorten.junit5

import com.github.urlshorten.UrlRepository
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

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
        assert shortenUrl.equals(url.orElse(null)?.shortenUrl)
    }
}
