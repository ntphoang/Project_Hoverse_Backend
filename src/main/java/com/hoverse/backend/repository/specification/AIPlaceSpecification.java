package com.hoverse.backend.repository.specification;

import com.hoverse.backend.entity.Place;
import com.hoverse.backend.enums.PlaceStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/08/2026
 */
public class AIPlaceSpecification {
    public static Specification<Place> hasActiveStatus(){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), PlaceStatus.APPROVED);
    }

    public static Specification<Place> matchesLocation(String location){
        return (root, query, criteriaBuilder) -> {
            if(location == null || location.trim().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            String searchLocation = location.toLowerCase().trim();
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("address")),
                    "%"+searchLocation+"%"
            );
        };
    }

    public static Specification<Place> matchesCategorySlug(String categorySlug){
        return (root, query, criteriaBuilder) -> {
            if(categorySlug == null || categorySlug.trim().isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("category").get("slug"), "%"+categorySlug.toLowerCase().trim()+"%");
        };
    }
}
