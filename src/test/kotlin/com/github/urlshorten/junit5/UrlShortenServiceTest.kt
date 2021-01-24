package com.github.urlshorten.junit4

import com.github.urlshorten.Url
import com.github.urlshorten.UrlRepository
import com.github.urlshorten.UrlShortenService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import java.time.LocalDateTime

class UrlShortenServiceTest {
    @Mock
    lateinit var repository: UrlRepository
    lateinit var service: UrlShortenService

    @BeforeEach
    fun set_up() {
        MockitoAnnotations.initMocks(this)
        service = UrlShortenService(repository)
    }

    @AfterEach
    fun tear_down() {
        Mockito.verifyNoMoreInteractions(repository)
    }

    @Test
    fun should_retrieve() {
        // given
        val url = "https://google.com"
        val entity = Url(
                shortenUrl = null,
                originalUrl = "",
                expirationDate = LocalDateTime.now(),
                creationDate = LocalDateTime.now(),
                id = null,
        )
        BDDMockito.given(repository.findByShortenUrl(ArgumentMatchers.anyString())).willReturn(entity)

        // when
        val optUrl = service.retrieve(url)

        // then
        Assertions.assertSame(entity, optUrl)
        Mockito.verify(repository)!!.findByShortenUrl(url)
    }
}