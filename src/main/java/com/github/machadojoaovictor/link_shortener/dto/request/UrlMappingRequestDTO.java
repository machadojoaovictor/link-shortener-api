package com.github.machadojoaovictor.link_shortener.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

public record UrlMappingRequestDTO(
        @URL(message = "Invalid URL") @Size(max = 2048) @NotBlank(message = "Required field") String originalUrl,
        @Future Instant expiresAt,
        @Positive Long maxClicks) {
}