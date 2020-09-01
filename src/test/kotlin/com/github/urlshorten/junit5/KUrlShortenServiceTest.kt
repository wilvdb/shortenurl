package com.github.urlshorten.junit5

import com.github.urlshorten.IdStrategy
import com.github.urlshorten.Url
import com.github.urlshorten.UrlRepository
import com.github.urlshorten.UrlShortenService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import java.util.*

class KUrlShortenServiceTest {
    @Mock
    lateinit var repository: UrlRepository

    @Captor
    lateinit var urlCaptor: ArgumentCaptor<Url>

    lateinit var service: UrlShortenService

    @BeforeEach
    fun set_up() {
        MockitoAnnotations.initMocks(this)

        service = UrlShortenService(repository)
    }

    @AfterEach
    fun tear_down() {
        verifyNoMoreInteractions(repository);
    }

    @Nested
    inner class UrlShortenServiceShortenUrl {

        @BeforeEach
        fun set_up() {
            given(repository.save(any(Url::class.java))).willAnswer { invocation: InvocationOnMock ->
                val urlEntity: Url = invocation.getArgument(0)
                urlEntity.id = 1L
                urlEntity
            }
        }

        @ParameterizedTest(name = "strategy {0}")
        @EnumSource(IdStrategy::class)
        fun `should shorten url based on `(strategy: IdStrategy) {
            // given
            val goodUrl = "https://medium.com/swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c"

            // when
            val result = service.shorten(goodUrl, strategy)

            // then
            verify(repository).save(urlCaptor.capture())
            val url = urlCaptor.value
            assertAll(
                    { assertEquals(goodUrl, url.originalUrl) },
                    { assertNotNull(url.id) })

            assertTrue(result.shortenUrlContains("medium.com"))
            assertFalse(result.shortenUrlContains("swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c"))
        }

        @Test
        fun `should failed to shorten`() {
            // given
            val wrongUrl = "hmerhmeoj(you'tueido"

            // then
            assertThrows<IllegalArgumentException> { service.shorten(wrongUrl, IdStrategy.BASE_62) }

            verify(repository).save(urlCaptor.capture())
            val url = urlCaptor.value
            assertAll(
                    { assertEquals(wrongUrl, url.originalUrl) },
                    { assertNotNull(url.id) })
        }
    }

    @Test
    fun `should retrieve`() {
        // given
        val url = "https://google.com"
        val entity = Url()
        given(repository.findByShortenUrl(anyString())).willReturn(Optional.of(entity))

        // when
        val optUrl = service.retrieve(url)

        // then
        assertTrue(optUrl.isPresent)
        assertSame(entity, optUrl.get())

        verify(repository).findByShortenUrl(url)
    }
}