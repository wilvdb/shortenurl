package com.github.urlshorten;

import java.time.LocalDateTime;

public class ShortenedUrl {

    private String shortenUrl;
    private String originalUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    public String getShortenUrl() {
        return shortenUrl;
    }

    public void setShortenUrl(String shortenUrl) {
        this.shortenUrl = shortenUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "ShortenedUrl{" +
                "shortenUrl='" + shortenUrl + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
