package com.hoverse.backend.entity;

import com.hoverse.backend.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 09/07/2026
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "place_media")
public class PlaceMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String publicId;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    private LocalDateTime createdAt;

    private boolean thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id",nullable = false)
    private Place place;

    @PrePersist
    protected void onCreate(){
        if(createdAt == null){
            createdAt = LocalDateTime.now();
        }
    }
}
