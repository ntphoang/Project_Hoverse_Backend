package com.hoverse.backend.entity;

import com.hoverse.backend.enums.PlaceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(
        name = "places",
        indexes = {
                @Index(name = "idx_place_category",columnList = "category_id"),
                @Index(name = "idx_place_location",columnList = "latitude,longitude"),
                @Index(name = "idx_place_normalized_address", columnList = "normalized_address"),
                @Index(name = "idx_place_avg_rating", columnList = "avg_rating")
        }
)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(name = "normalized_address", length = 500)
    private String normalizedAddress;

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "avg_rating", precision = 3, scale = 2)
    private BigDecimal avgRating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "view_count")
    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PlaceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "place",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceMedia> placeMediaList;

    @ManyToMany()
    @JoinTable(
            name = "place_tags",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @PrePersist
    protected void onCreate(){
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if(this.status == null) this.status = PlaceStatus.APPROVED;
        if(this.avgRating == null) this.avgRating = BigDecimal.ZERO;
        if(this.reviewCount == null) this.reviewCount = 0;
        if(this.viewCount == null) this.viewCount = 0;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
