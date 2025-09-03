package com.github.machadojoaovictor.link_shortener.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
public class ValidationErrorDTO extends StandardErrorDTO {

    private final List<FieldErrorDTO> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        FieldErrorDTO fieldErrorDTO = FieldErrorDTO.builder()
                .fieldName(fieldName)
                .message(message)
                .build();
        errors.add(fieldErrorDTO);
    }
}