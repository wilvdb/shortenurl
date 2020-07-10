package com.github.urlshorten;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
public class UrlShortenController {

    private final UrlShortenService urlShortenService;
    private final Mapper mapper;

    public UrlShortenController(UrlShortenService urlShortenService, Mapper mapper) {
        this.urlShortenService = urlShortenService;
        this.mapper = mapper;
    }

    @PostMapping("/url_shorten")
    public ResponseEntity shorten(@RequestBody UrlToShorten url) {
        var shortenedUrl = urlShortenService.shorten(url.getUrl(), mapper.toIdStrategy(url.getStrategy()));

        return ResponseEntity.created(URI.create(shortenedUrl.getShortenUrl())).build();
    }

    @GetMapping("/url_shorten/{url}")
    public ResponseEntity<ShortenedUrl> retrieve(@PathVariable("url") String url) {
        return ResponseEntity.of(urlShortenService.retrieve(url).map(mapper::toDto));
    }
}
