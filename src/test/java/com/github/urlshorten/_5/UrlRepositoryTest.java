package com.github.urlshorten._5;

import com.github.urlshorten.UrlRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@Tag("db")
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
        assertTrue(url.isPresent());
        assertEquals(shortenUrl, url.get().getShortenUrl());
    }

}