package com.github.machadojoaovictor.link_shortener.service;

import com.github.machadojoaovictor.link_shortener.dto.response.UrlMappingResponseDTO;
import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;
import com.github.machadojoaovictor.link_shortener.mapper.UrlMappingMapper;
import com.github.machadojoaovictor.link_shortener.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.concurrent.atomic.AtomicLong;

import static com.github.machadojoaovictor.link_shortener.utils.CodeGenerator.generateBase62;
import static com.github.machadojoaovictor.link_shortener.utils.CodeGenerator.toBase62;

@RequiredArgsConstructor
@Service
public class UrlMappingService {

    private final UrlMappingRepository repository;
    private final UrlMappingMapper mapper;

    private static final AtomicLong counter = new AtomicLong(1);

    @Transactional
    public UrlMappingResponseDTO shortenUrl(String originalUrl) {
        String basePart = toBase62(counter.incrementAndGet());
        String randomPart = generateBase62(4);
        String shortCode = basePart + randomPart;

        UrlMapping entity = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .build();

        repository.save(entity);
        return mapper.toDTO(entity);
    }
}
