package com.github.urlshorten.junit4;

import com.github.urlshorten.Url;
import com.github.urlshorten.UrlRepository;
import com.github.urlshorten.UrlShortenService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.*;

public class UrlShortenServiceTest {

  @Mock
  UrlRepository repository;

  UrlShortenService service;

  @Before
  public void set_up() {
    MockitoAnnotations.initMocks(this);

    service = new UrlShortenService(repository);
  }

  @After
  public void tear_down() {
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void should_retrieve() {
    // given
    var url = "https://google.com";
    var entity = new Url();
    given(repository.findByShortenUrl(anyString())).willReturn(Optional.of(entity));

    // when
    var optUrl = service.retrieve(url);

    // then
    assertTrue(optUrl.isPresent());
    assertSame(entity, optUrl.get());

    verify(repository).findByShortenUrl(url);
  }
}