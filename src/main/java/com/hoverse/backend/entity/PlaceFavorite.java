package com.hoverse.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/07/2026
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "place_favorites")
@IdClass(PlaceFavorite.PlaceFavoriteId.class)
public class PlaceFavorite {
    @Id
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class PlaceFavoriteId implements Serializable {
        private Long place;
        private Long user;
    }
}



