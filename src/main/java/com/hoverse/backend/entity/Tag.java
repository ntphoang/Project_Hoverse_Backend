package com.hoverse.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "icon_name", nullable = false)
    private String iconName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Place> places;

    @PrePersist
    protected void onCreate(){
        if(this.isActive == null) this.isActive = true;
    }
}
