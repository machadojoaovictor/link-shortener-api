package com.github.machadojoaovictor.link_shortener.controller;

import com.github.machadojoaovictor.link_shortener.config.AppProperties;
import com.github.machadojoaovictor.link_shortener.dto.request.UrlMappingRequestDTO;
import com.github.machadojoaovictor.link_shortener.dto.response.UrlMappingResponseDTO;
import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;
import com.github.machadojoaovictor.link_shortener.mapper.UrlMappingMapper;
import com.github.machadojoaovictor.link_shortener.service.UrlMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/links")
public class UrlMappingController {

    private final UrlMappingService service;
    private final AppProperties properties;

    @PostMapping
    public ResponseEntity<UrlMappingResponseDTO> shortenUrl(
            @Valid @RequestBody UrlMappingRequestDTO requestDTO) throws URISyntaxException {

        UrlMapping entity = service.shortenUrl(requestDTO);

        String shortUrl = buildShortUrl(entity.getShortCode());

        UrlMappingResponseDTO responseDTO = UrlMappingMapper.toResponseDTO(entity, shortUrl);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    private String buildShortUrl(String shortCode) {
        if (properties.baseUrl() != null && !properties.baseUrl().isBlank()) {
            return properties.baseUrl() + "/links/" + shortCode;
        }

        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{shortCode}")
                .buildAndExpand(shortCode)
                .toUriString();
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = service.processRedirectAndGetOriginalUrl(shortCode);
        URI location = URI.create(originalUrl);
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(location).build();
    }
}
