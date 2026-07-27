package com.hoverse.backend.entity;

import com.hoverse.backend.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 27/07/2026
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "review_media")
public class ReviewMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String publicId;

    @Enumerated(EnumType.STRING)
    private MediaType type;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id",nullable = false)
    private Review review;

    @PrePersist
    protected void onCreate(){
        if(createdAt == null){
            createdAt = LocalDateTime.now();
        }
    }
}
