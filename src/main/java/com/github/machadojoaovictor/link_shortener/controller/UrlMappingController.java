package com.github.machadojoaovictor.link_shortener.controller;

import com.github.machadojoaovictor.link_shortener.dto.request.UrlMappingRequestDTO;
import com.github.machadojoaovictor.link_shortener.dto.response.UrlMappingResponseDTO;
import com.github.machadojoaovictor.link_shortener.service.UrlMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/links")
public class UrlMappingController {

    private final UrlMappingService service;

    @PostMapping
    public ResponseEntity<UrlMappingResponseDTO> shortenUrl(@Valid @RequestBody UrlMappingRequestDTO requestDTO) {
        String originalUrl = requestDTO.originalUrl();
        UrlMappingResponseDTO responseDTO = service.shortenUrl(originalUrl);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

}
