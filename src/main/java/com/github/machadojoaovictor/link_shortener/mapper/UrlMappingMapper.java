package com.github.machadojoaovictor.link_shortener.mapper;

import com.github.machadojoaovictor.link_shortener.dto.request.UrlMappingRequestDTO;
import com.github.machadojoaovictor.link_shortener.dto.response.UrlMappingResponseDTO;
import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;

public class UrlMappingMapper {

    public static UrlMapping toEntity(UrlMappingRequestDTO requestDTO, String shortCode) {
        return UrlMapping.builder()
                .originalUrl(requestDTO.originalUrl())
                .shortCode(shortCode)
                .expiresAt(requestDTO.expiresAt())
                .maxClicks(requestDTO.maxClicks())
                .build();
    }

    public static UrlMappingResponseDTO toResponseDTO(UrlMapping entity, String shortUrl) {
        return UrlMappingResponseDTO.builder()
                .originalUrl(entity.getOriginalUrl())
                .shortUrl(shortUrl)
                .shortCode(entity.getShortCode())
                .createdAt(entity.getCreatedAt())
                .expiresAt(entity.getExpiresAt())
                .maxClicks(entity.getMaxClicks())
                .status(String.valueOf(entity.getStatus()))
                .build();
    }
}
