package com.github.machadojoaovictor.link_shortener.controller;

import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;
import com.github.machadojoaovictor.link_shortener.service.UrlMappingService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UrlMapping> shortenUrl(@RequestBody  String originalUrl) {
        UrlMapping url = service.shortenUrl(originalUrl);
        return ResponseEntity.ok(url);
    }

}
