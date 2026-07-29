package com.hoverse.backend.repository.specification;

import com.hoverse.backend.entity.Place;
import com.hoverse.backend.entity.Tag;
import com.hoverse.backend.enums.PlaceStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

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

    // Hàm lọc theo status
    public static Specification<Place> hasStatus(PlaceStatus status){
        return (root, query, criteriaBuilder) -> {
          if(status == null) {
              return criteriaBuilder.conjunction();
          }
          return criteriaBuilder.like(root.get("status"),status.toString());
        };
    }

    // Hàm lọc theo Category
    public static Specification<Place> hasCategory(Long categoryId){
        return(root, query, criteriaBuilder) -> {
          if(categoryId == null){
              return criteriaBuilder.conjunction();
          }
          return criteriaBuilder.equal(root.get("category").get("id"),categoryId);
        };
    }

    // Hàm lọc theo List<Tag>
    public static Specification<Place> hasAllTags(List<Long> tagIds) {
        return (root, query, criteriaBuilder) -> {
            if (tagIds == null || tagIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Subquery<Long> subquery = query.subquery(Long.class);

            Root<Place> subroot = subquery.correlate(root);
            Join<Place,Tag> subTags = subroot.join("tags");

            subquery.where(subTags.get("id").in(tagIds));
            subquery.select(criteriaBuilder.count(subTags));

            return criteriaBuilder.equal(subquery, tagIds.size());
        };
    }
}
