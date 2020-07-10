package com.github.urlshorten;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UrlShortenService {

    private final UrlRepository urlRepository;

    public UrlShortenService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public Url shorten(String url, IdStrategy strategy) {
        Objects.requireNonNull(url);
        Objects.requireNonNull(strategy);

        Url urlForShorten = new Url();
        urlForShorten.setCreationDate(LocalDateTime.now());
        urlForShorten.setExpirationDate(urlForShorten.getCreationDate().plusYears(2L));
        urlForShorten.setOriginalUrl(url);

        urlRepository.save(urlForShorten);

        String id = strategy.apply(urlForShorten.getId());
        try {
            URL netUrl = new URL(url);

            urlForShorten.setShortenUrl(url.replace(netUrl.getPath(), id));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }

        return urlForShorten;
    }

    @Transactional(readOnly = true)
    public Optional<Url> retrieve(String shortenUrl) {
        Objects.requireNonNull(shortenUrl);

        return urlRepository.findByShortenUrl(shortenUrl);
    }
}
