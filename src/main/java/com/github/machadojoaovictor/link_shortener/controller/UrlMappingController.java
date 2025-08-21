package com.github.machadojoaovictor.link_shortener.controller;

import com.github.machadojoaovictor.link_shortener.dto.request.UrlMappingRequestDTO;
import com.github.machadojoaovictor.link_shortener.dto.response.UrlMappingResponseDTO;
import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;
import com.github.machadojoaovictor.link_shortener.service.UrlMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/links")
public class UrlMappingController {

    private final UrlMappingService service;

    @PostMapping
    public ResponseEntity<UrlMappingResponseDTO> shortenUrl(@Valid @RequestBody UrlMappingRequestDTO requestDTO) {
        String originalUrl = requestDTO.originalUrl();
        LocalDateTime expiresAt = requestDTO.expiresAt();
        Integer maxClicks = requestDTO.maxClicks();

        UrlMappingResponseDTO responseDTO = service.shortenUrl(originalUrl, expiresAt, maxClicks);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        return service.getUrl(shortCode)
                .map(this::createRedirectResponse)
                .orElse(
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                );
    }

    private ResponseEntity<Void> createRedirectResponse(UrlMapping urlMapping) {
        URI uri = URI.create(urlMapping.getOriginalUrl());

        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(uri)
                .build();
    }
}
