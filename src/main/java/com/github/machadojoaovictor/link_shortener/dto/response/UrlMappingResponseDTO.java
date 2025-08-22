package com.github.machadojoaovictor.link_shortener.dto.response;

import java.time.LocalDateTime;

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