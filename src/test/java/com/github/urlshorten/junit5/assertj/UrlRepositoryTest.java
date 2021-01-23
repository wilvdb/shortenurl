package com.github.urlshorten.junit5.assertj;

import com.github.urlshorten.UrlRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Tag("db")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UrlRepositoryTest {

  @Autowired
  UrlRepository repository;

  @Test
  void should_find_by_shortern_url() {
    // given
    var shortenUrl = "http://google.com/1234";

    // when
    var url = repository.findByShortenUrl(shortenUrl);

    // then
    assertThat(url)
        .isPresent()
        .get().hasFieldOrPropertyWithValue("shortenUrl", shortenUrl);
  }

}