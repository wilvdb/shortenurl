package com.github.urlshorten;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    ShortenedUrl toDto(Url url);

    IdStrategy toIdStrategy(UrlToShorten.GeneratorStrategy strategy);
}
