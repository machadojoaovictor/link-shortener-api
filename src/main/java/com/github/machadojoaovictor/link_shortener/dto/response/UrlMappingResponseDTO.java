package com.github.machadojoaovictor.link_shortener.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UrlMappingResponseDTO(
        String originalUrl,
        String newUrl,
        String shortCode,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        Long maxClicks,
        Long currentClicks,
        String status
) {
}