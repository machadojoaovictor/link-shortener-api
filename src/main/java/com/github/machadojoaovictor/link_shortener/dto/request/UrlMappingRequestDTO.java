package com.github.machadojoaovictor.link_shortener.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public record UrlMappingRequestDTO(
        @URL(message = "Invalid URL") @Size(max = 2048) @NotBlank(message = "Required field") String originalUrl,
        @Future @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime expiresAt,
        @Positive Long maxClicks) {
}
