package com.hoverse.backend.repository.specification;

import com.hoverse.backend.entity.Review;
import com.hoverse.backend.enums.ReviewStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 21/08/2026
 */
public class ReviewSpecification{
    public static Specification<Review> hasMonth(Integer month){
        return ((root, query, criteriaBuilder) -> {
            if(month == null || month < 1 || month > 12){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.function("MONTH", Integer.class, root.get("createdAt"))
                    ,month);
        });
    }

    public static Specification<Review> hasYear(Integer year){
        return ((root, query, criteriaBuilder) -> {
            if(year == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("createdAt"))
                    ,year);
        });
    }

    public static Specification<Review> hasStatus(ReviewStatus status){
        return ((root, query, criteriaBuilder) -> {
            if(status == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"),status);
        });
    }

    public static Specification<Review> hasRating(Integer rating){
        return ((root, query, criteriaBuilder) -> {
            if(rating == null || rating < 1 || rating > 5){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("rating"),rating);
        });
    }
}
