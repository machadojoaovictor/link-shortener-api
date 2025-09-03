package com.github.machadojoaovictor.link_shortener.dto.response;

import lombok.Builder;

@Builder
public record FieldErrorDTO(String fieldName, String message) {
}