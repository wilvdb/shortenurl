package com.github.urlshorten.junit5.assertj;

import com.github.urlshorten.IdStrategy;
import com.github.urlshorten.Url;
import com.github.urlshorten.UrlRepository;
import com.github.urlshorten.UrlShortenService;
import com.github.urlshorten.junit5.TestedUrl;
import com.github.urlshorten.junit5.TestedUrlAggregator;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.*;

@ExtendWith(SoftAssertionsExtension.class)
class UrlShortenServiceTest {

  @Mock
  UrlRepository repository;

  @Captor
  ArgumentCaptor<Url> urlCaptor;

  UrlShortenService service;

  @BeforeEach
  void set_up() {
    MockitoAnnotations.initMocks(this);

    service = new UrlShortenService(repository);
  }

  @AfterEach
  void tear_down() {
    verifyNoMoreInteractions(repository);
  }

  @Nested
  class UrlShortenServiceShortenUrl {

    @BeforeEach
    void set_up() {
      given(repository.save(any(Url.class))).willAnswer(invocation -> {
        var urlEntity = (Url) invocation.getArgument(0);
        urlEntity.setId(123456789L);
        return urlEntity;
      });
    }

    @DisplayName("should shorten url based on...")
    @ParameterizedTest(name = " strategy {0}")
    @EnumSource(IdStrategy.class)
    void should_shortern_url(IdStrategy strategy, SoftAssertions soft) {
      // given
      var goodUrl = "https://medium.com/swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c";

      // when
      var result = service.shorten(goodUrl, strategy);

      // then
      verify(repository).save(urlCaptor.capture());
      var url = urlCaptor.getValue();
      soft.assertThat(url.getOriginalUrl()).isEqualTo(goodUrl);
      soft.assertThat(url.getId()).isNotNull();

      assertThat(result.getShortenUrl())
          .contains("medium.com")
          .doesNotContain("swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c");
    }

    @DisplayName("should shorten urls...")
    @ParameterizedTest(name = "shorten {0}")
    @CsvFileSource(resources = "/urls.csv")
    void should_shorten(@AggregateWith(TestedUrlAggregator.class) TestedUrl testedUrl, SoftAssertions soft) {
      soft.assertThat(service.shorten(testedUrl.getOriginUrl(), testedUrl.getStrategy()).getShortenUrl())
          .isEqualTo(testedUrl.getTargetUrl());

      verify(repository).save(urlCaptor.capture());
      var url = urlCaptor.getValue();
      soft.assertThat(url.getOriginalUrl()).isEqualTo(testedUrl.getOriginUrl());
      soft.assertThat(url.getShortenUrl()).isEqualTo(testedUrl.getTargetUrl());
    }


    @Test
    void should_failed_to_shorten(SoftAssertions soft) {
      // given
      var wrongUrl = "hmerhmeoj(you'tueido";

      // when
      ThrowableAssert.ThrowingCallable callable = () -> service.shorten(wrongUrl, IdStrategy.BASE_62);

      // then
      catchThrowableOfType(callable, IllegalArgumentException.class);

      verify(repository).save(urlCaptor.capture());
      var url = urlCaptor.getValue();
      soft.assertThat(url.getOriginalUrl()).isEqualTo(wrongUrl);
      soft.assertThat(url.getId()).isNotNull();
    }
  }

  @Test
  void should_retrieve() {
    // given
    var url = "https://google.com";
    var entity = new Url();
    given(repository.findByShortenUrl(anyString())).willReturn(Optional.of(entity));

    // when
    var optUrl = service.retrieve(url);

    // then
    assertThat(optUrl)
        .isPresent()
        .get()
        .isSameAs(entity);

    verify(repository).findByShortenUrl(url);
  }

}