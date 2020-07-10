package com.github.urlshorten;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    UrlRepository repository;

    @Test
    void should_find_by_shortern_url() {
        // given
        String shortenUrl = "http://google.com/1234";

        // when
        Optional<Url> url = repository.findByShortenUrl(shortenUrl);

        // then
        assertTrue(url.isPresent());
        assertEquals(shortenUrl, url.get().getShortenUrl());
    }
}