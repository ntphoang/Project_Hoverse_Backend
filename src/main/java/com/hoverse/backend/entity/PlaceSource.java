package com.hoverse.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(
        name = "place_sources",
        indexes = {
                @Index(name = "idx_place_source_place", columnList = "place_id")
        }
)
public class PlaceSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "source_url", nullable = false, length = 1000)
    private String sourceUrl;

    @Column(name = "creator_username", nullable = false, length = 100)
    private String creatorUsername;

    @Column(name = "permission_granted_at", nullable = false)
    private LocalDateTime permissionGrantedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.permissionGrantedAt == null) {
            this.permissionGrantedAt = LocalDateTime.now();
        }
    }
}