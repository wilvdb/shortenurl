package com.github.urlshorten.junit5;

import com.github.urlshorten.IdStrategy;

import java.util.Objects;

public class TestedUrl {
  private final String originUrl;
  private final String targetUrl;
  private final IdStrategy strategy;

  public TestedUrl(String originUrl, String targetUrl, IdStrategy strategy) {
    this.originUrl = Objects.requireNonNull(originUrl);
    this.targetUrl = Objects.requireNonNull(targetUrl);
    this.strategy = Objects.requireNonNull(strategy);
  }

  public String getOriginUrl() {
    return originUrl;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public IdStrategy getStrategy() {
    return strategy;
  }

  @Override
  public String toString() {
    return "TestedUrl{" +
        "originUrl='" + originUrl + '\'' +
        ", targetUrl='" + targetUrl + '\'' +
        ", strategy=" + strategy +
        '}';
  }
}
