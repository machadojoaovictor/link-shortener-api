package com.github.machadojoaovictor.link_shortener.service;

import com.github.machadojoaovictor.link_shortener.dto.request.UrlMappingRequestDTO;
import com.github.machadojoaovictor.link_shortener.entity.UrlMapping;
import com.github.machadojoaovictor.link_shortener.entity.enums.UrlMappingStatus;
import com.github.machadojoaovictor.link_shortener.exception.BadRequestException;
import com.github.machadojoaovictor.link_shortener.exception.ResourceNotFoundException;
import com.github.machadojoaovictor.link_shortener.mapper.UrlMappingMapper;
import com.github.machadojoaovictor.link_shortener.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.time.Instant;

import static com.github.machadojoaovictor.link_shortener.utils.CodeGenerator.generateBase62;

@RequiredArgsConstructor
@Service
public class UrlMappingService {

    private final UrlMappingRepository repository;

    @Transactional
    public UrlMapping shortenUrl(UrlMappingRequestDTO requestDTO) throws URISyntaxException {
        String shortCode;

        do {
            shortCode = generateBase62(7);
        } while (repository.findByShortCode(shortCode).isPresent());

        UrlMapping entity = UrlMappingMapper.toEntity(requestDTO, shortCode);
        return repository.save(entity);
    }

    @Transactional(noRollbackFor = BadRequestException.class)
    public String processRedirectAndGetOriginalUrl(String shortCode) {
        UrlMapping urlMapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("URL not found"));

        if (urlMapping.getStatus() == UrlMappingStatus.DEACTIVATED) {
            throw new BadRequestException("This link is deactivated.");
        }

        if (urlMapping.getExpiresAt() != null && Instant.now().isAfter(urlMapping.getExpiresAt())) {
            urlMapping.setStatus(UrlMappingStatus.EXPIRED);
            repository.save(urlMapping);
            throw new BadRequestException("This link has expired.");
        }
        if (urlMapping.getMaxClicks() != null && urlMapping.getClicks() >= urlMapping.getMaxClicks()) {
            urlMapping.setStatus(UrlMappingStatus.LIMIT_REACHED);
            repository.save(urlMapping);
            throw new BadRequestException("This link has reached its maximum number of clicks.");
        }

        urlMapping.addClick();
        repository.save(urlMapping);

        return urlMapping.getOriginalUrl();
    }
}
