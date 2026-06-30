package com.hoverse.backend.repository.specification;

import com.hoverse.backend.entity.Place;
import org.springframework.data.jpa.domain.Specification;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 30/06/2026
 */
public class PlaceSpecification {
    // Hàm lọc theo title
    public static Specification<Place> hasTitle(String title){
        return (root, query, criteriaBuilder) -> {
          if(title == null || title.trim().isEmpty()){
              return criteriaBuilder.conjunction();
          }
          return criteriaBuilder.like(root.get("title"),"%"+title+"%");
        };
    }

    // Hàm lọc theo avgRating nhỏ nhất
    public static Specification<Place> hasMinRating(Double minRating){
        return (root, query,criteriaBuilder)-> {
            if(minRating == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("avgRating"),minRating);
        };
    }
}
