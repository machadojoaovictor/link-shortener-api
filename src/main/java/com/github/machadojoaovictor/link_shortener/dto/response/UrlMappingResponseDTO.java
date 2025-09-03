package com.github.machadojoaovictor.link_shortener.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UrlMappingResponseDTO(
        String originalUrl,
        String shortUrl,
        String shortCode,
        Instant createdAt,
        Instant expiresAt,
        Long maxClicks,
        String status
) {
}