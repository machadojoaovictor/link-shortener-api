package com.github.machadojoaovictor.link_shortener.service;

import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;
import com.github.machadojoaovictor.link_shortener.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UrlMappingService {

    private final UrlMappingRepository repository;

    private static long counter = 1L;
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Transactional
    public UrlMapping shortenUrl(String originalUrl, LocalDateTime expireAt) {
        String shortCode = toBase62(counter++);

        UrlMapping urlMapping = UrlMapping.builder().originalUrl(originalUrl).shortCode(shortCode).expireAt(expireAt).build();

        repository.save(urlMapping);

        return urlMapping;
    }

    @Transactional(readOnly = true)
    public UrlMapping getUrl(String shortCode) {
        return repository.findByShortCode(shortCode).orElseThrow(
                () -> new RuntimeException("Err")
        );
    }

    @Transactional
    public void incrementClicks(UrlMapping urlMapping) {
        urlMapping.addClick();
        repository.save(urlMapping);
    }


    private String toBase62(long num) {
        if (num == 0) return "0";

        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            sb.append(ALPHABET.charAt((int) (num % 62)));
            num /= 62;
        }

        return sb.reverse().toString();
    }

}
