package com.github.urlshorten._4;

import com.github.urlshorten.UrlRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlRepositoryTest {

  @Autowired
  UrlRepository repository;

  @Test
  public void should_find_by_shortern_url() {
    // given
    var shortenUrl = "http://google.com/1234";

    // when
    var url = repository.findByShortenUrl(shortenUrl);

    // then
    Assert.assertTrue(url.isPresent());
    Assert.assertEquals(shortenUrl, url.get().getShortenUrl());
  }

}