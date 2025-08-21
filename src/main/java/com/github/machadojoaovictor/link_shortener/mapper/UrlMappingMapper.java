package com.github.machadojoaovictor.link_shortener.mapper;

import com.github.machadojoaovictor.link_shortener.dto.response.UrlMappingResponseDTO;
import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.net.URI;

@Mapper(componentModel = "spring")
public interface UrlMappingMapper {

    @Mapping(target = "newUrl", expression = "java(buildNewUrl(entity))")
    UrlMappingResponseDTO toDTO(UrlMapping entity);

    default String buildNewUrl(UrlMapping entity) {
        try {
            URI uri = new URI(entity.getOriginalUrl());
            String baseUrl = uri.getScheme() + "://" + uri.getHost();
            return baseUrl + "/" + entity.getShortCode();
        } catch (Exception e) {
            throw new RuntimeException("Invalid URL: " + entity.getOriginalUrl(), e);
        }
    }
}
