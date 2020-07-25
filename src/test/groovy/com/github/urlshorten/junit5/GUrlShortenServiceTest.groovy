package com.github.urlshorten.junit5

import com.github.urlshorten.IdStrategy
import com.github.urlshorten.Url
import com.github.urlshorten.UrlRepository
import com.github.urlshorten.UrlShortenService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.junit.jupiter.api.Assertions.assertAll
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.BDDMockito.given
import static org.mockito.Mockito.verify

class GUrlShortenServiceTest {
    @Mock
    UrlRepository repository

    @Captor
    ArgumentCaptor<Url> urlCaptor

    UrlShortenService service

    @BeforeEach
    void set_up() {
        MockitoAnnotations.initMocks(this)

        service = new UrlShortenService(repository)
    }

    @Nested
    class UrlShortenServiceShortenUrl {

        @BeforeEach
        void set_up() {
            given(repository.save(any(Url))).willAnswer({ invocation ->
                Url urlEntity = invocation.getArgument(0)
                urlEntity.id = 1L
                urlEntity
            })
        }

        @ParameterizedTest(name = "strategy {0}")
        @EnumSource(IdStrategy)
        void "should shorten url based on "(IdStrategy strategy) {
            // given
            def goodUrl = "https://medium.com/swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c"

            // when
            def result = service.shorten(goodUrl, strategy)

            // then
            verify(repository).save(urlCaptor.capture())
            def url = urlCaptor.value
            assertAll({ assert goodUrl == url.originalUrl } as Executable,
                    { assert url.id != null } as Executable)

            assert result.shortenUrl.contains("medium.com")
            assert !result.shortenUrl.contains("swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c")
        }


        @Test
        void "should failed to shorten"() {
            // given
            def wrongUrl = "hmerhmeoj(you'tueido"

            // then
            assertThrows(IllegalArgumentException) { service.shorten(wrongUrl, IdStrategy.BASE_62) }

            verify(repository).save(urlCaptor.capture())
            def url = urlCaptor.value
            assertAll(
                    { assert wrongUrl == url.originalUrl } as Executable,
                    { assert url.id != null } as Executable)
        }
    }

    @Test
    void "should retrieve"() {
        // given
        def url = "https://google.com"
        def entity = new Url()
        given(repository.findByShortenUrl(anyString())).willReturn(Optional.of(entity))

        // when
        def optUrl = service.retrieve(url)

        // then
        assert optUrl.isPresent()
        assert entity == optUrl.get()

        verify(repository).findByShortenUrl(url)
    }
}
