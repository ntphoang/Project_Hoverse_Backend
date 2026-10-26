package com.hoverse.backend.repository.specification;

import com.hoverse.backend.entity.Category;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.enums.PlaceStatus;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 14/08/2026
 */
public class CategorySpecification{
    public static Specification<Category> isActive(Boolean isActive){
        return (root, query, criteriaBuilder) -> {
            if(isActive == null)return criteriaBuilder.conjunction();

            return criteriaBuilder.equal(root.get("isActive"),isActive);
        };
    }

    public static Specification<Category> hasPlace(Boolean hasPlace) {
        return (root, query, criteriaBuilder) -> {
            if (hasPlace == null) {
                return criteriaBuilder.conjunction();
            }

            Subquery<Long> subquery = query.subquery(Long.class);
            var placeRoot = subquery.from(Place.class);

            subquery.select(placeRoot.get("id"))
                    .where(
                            criteriaBuilder.equal(
                                    placeRoot.get("category").get("id"),
                                    root.get("id")
                            ),
                            criteriaBuilder.equal(
                                    placeRoot.get("status"),
                                    PlaceStatus.APPROVED
                            )
                    );

            if (hasPlace) {
                return criteriaBuilder.exists(subquery);
            }

            return criteriaBuilder.not(criteriaBuilder.exists(subquery));
        };
    }
}
