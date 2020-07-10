package com.github.urlshorten;

public class UrlToShorten {

    enum GeneratorStrategy {
        BASE_10, BASE_62
    }

    private String url;

    private GeneratorStrategy strategy;

    public GeneratorStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(GeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlToShorten{" +
                "url='" + url + '\'' +
                ", strategy=" + strategy +
                '}';
    }

}
