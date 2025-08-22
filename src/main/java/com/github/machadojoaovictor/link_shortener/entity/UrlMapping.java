package com.github.machadojoaovictor.link_shortener.entity;

import com.github.machadojoaovictor.link_shortener.entity.enums.UrlMappingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false, length = 20, unique = true)
    private String shortCode;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private Long maxClicks;

    @Builder.Default
    @Column(nullable = false)
    private Long clicks = 0L;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UrlMappingStatus status = UrlMappingStatus.ACTIVE;

    public void addClick() {
        this.clicks++;
    }
}
