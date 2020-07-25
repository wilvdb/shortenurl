package com.github.urlshorten.junit4;

import com.github.urlshorten.IdStrategy;
import com.github.urlshorten.Url;
import com.github.urlshorten.UrlRepository;
import com.github.urlshorten.UrlShortenService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.*;

public class UrlShortenServiceShortenFailureJ4Test {

  @Mock
  UrlRepository repository;

  @Captor
  ArgumentCaptor<Url> urlCaptor;

  UrlShortenService service;

  @Before
  public void set_up() {
    MockitoAnnotations.initMocks(this);

    service = new UrlShortenService(repository);
  }

  @Test
  public void should_failed_to_shorten() {
    // given
    var wrongUrl = "hmerhmeoj(you'tueido";
    given(repository.save(any(Url.class))).willAnswer(invocation -> {
      var urlEntity = (Url) invocation.getArgument(0);
      urlEntity.setId(1L);
      return urlEntity;
    });

    // when
    try {
      service.shorten(wrongUrl, IdStrategy.BASE_62);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // then
      verify(repository).save(urlCaptor.capture());
      var url = urlCaptor.getValue();
      Assert.assertEquals(wrongUrl, url.getOriginalUrl());
      Assert.assertNotNull(url.getId());
    }
  }

}