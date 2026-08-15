package com.hoverse.backend.repository.specification;

import com.hoverse.backend.entity.Category;
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
}
