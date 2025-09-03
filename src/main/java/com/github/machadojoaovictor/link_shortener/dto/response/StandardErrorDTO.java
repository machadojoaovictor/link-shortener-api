package com.github.machadojoaovictor.link_shortener.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class StandardErrorDTO {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
