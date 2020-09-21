package com.github.urlshorten.junit5.hamcrest;

import com.github.urlshorten.IdStrategy;
import com.github.urlshorten.Url;
import com.github.urlshorten.UrlRepository;
import com.github.urlshorten.UrlShortenService;
import com.github.urlshorten.junit5.TestedUrl;
import com.github.urlshorten.junit5.TestedUrlAggregator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.*;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
      given(repository.save(BDDMockito.any(Url.class))).willAnswer(invocation -> {
        var urlEntity = (Url) invocation.getArgument(0);
        urlEntity.setId(123456789L);
        return urlEntity;
      });
    }

    @DisplayName("should shorten url based on...")
    @ParameterizedTest(name = " strategy {0}")
    @EnumSource(IdStrategy.class)
    void should_shortern_url(IdStrategy strategy) {
      // given
      var goodUrl = "https://medium.com/swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c";

      // when
      var result = service.shorten(goodUrl, strategy);

      // then
      verify(repository).save(urlCaptor.capture());
      var url = urlCaptor.getValue();

      assertThat(goodUrl, equalTo(url.getOriginalUrl()));
      assertThat(url.getId(), notNullValue());

      assertThat(result.getShortenUrl(), allOf(
          containsString("medium.com"),
          not(containsString("swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c"))));

//      assertThat(result.getShortenUrl(), containsString("medium.com"));
//      assertThat(result.getShortenUrl(), not(containsString("swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c")));
    }

    @DisplayName("should shorten urls...")
    @ParameterizedTest(name = "shorten {0}")
    @CsvFileSource(resources = "/urls.csv")
    void should_shorten(@AggregateWith(TestedUrlAggregator.class) TestedUrl testedUrl) {
      assertEquals(service.shorten(testedUrl.getOriginUrl(), testedUrl.getStrategy()).getShortenUrl(),
          testedUrl.getTargetUrl());

      verify(repository).save(urlCaptor.capture());
      var url = urlCaptor.getValue();
      assertThat(testedUrl.getOriginUrl(), equalTo(url.getOriginalUrl()));
      assertThat(testedUrl.getTargetUrl(), equalTo(url.getShortenUrl()));
    }


    @Test
    void should_failed_to_shorten() {
      // given
      var wrongUrl = "hmerhmeoj(you'tueido";

      // when
      Executable executable = () -> service.shorten(wrongUrl, IdStrategy.BASE_62);

      // then
      assertThrows(IllegalArgumentException.class, executable);

      verify(repository).save(urlCaptor.capture());
      var url = urlCaptor.getValue();
      assertThat(wrongUrl, equalTo(url.getOriginalUrl()));
      assertThat(url.getId(), notNullValue());
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
    assertThat(optUrl.isPresent(), is(true));
    assertThat(entity, sameInstance(optUrl.get()));

    verify(repository).findByShortenUrl(url);
  }

}