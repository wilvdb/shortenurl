package com.github.urlshorten.junit4;

import com.github.urlshorten.IdStrategy;
import com.github.urlshorten.Url;
import com.github.urlshorten.UrlRepository;
import com.github.urlshorten.UrlShortenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(Parameterized.class)
public class UrlShortenServiceShortenUrlStrategyJ4Test {

  @Mock
  UrlRepository repository;

  @Captor
  ArgumentCaptor<Url> urlCaptor;

  UrlShortenService service;

  @Parameterized.Parameters
  public static Iterable<Object> data() {
    return Arrays.asList(IdStrategy.BASE_10, IdStrategy.BASE_62);
  }

  private final IdStrategy strategy;

  public UrlShortenServiceShortenUrlStrategyJ4Test(IdStrategy strategy) {
    this.strategy = strategy;
  }

  @Before
  public void set_up() {
    MockitoAnnotations.initMocks(this);

    service = new UrlShortenService(repository);

    given(repository.save(any(Url.class))).willAnswer(invocation -> {
      var urlEntity = (Url) invocation.getArgument(0);
      urlEntity.setId(1L);
      return urlEntity;
    });
  }

  @Test
  public void should_shortern_url() {
    // given
    var goodUrl = "https://medium.com/swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c";

    // when
    var result = service.shorten(goodUrl, strategy);

    // then
    verify(repository).save(urlCaptor.capture());
    var url = urlCaptor.getValue();
    assertAll(
        () -> assertEquals(goodUrl, url.getOriginalUrl()),
        () -> assertNotNull(url.getId()));

    assertTrue(result.getShortenUrl().contains("medium.com"));
    assertFalse(result.getShortenUrl().contains("swlh/how-to-build-a-tiny-url-service-that-scales-to-billions-f6fb5ea22e8c"));
  }
}