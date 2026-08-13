package com.hoverse.backend.repository.specification;

import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.Role;
import com.hoverse.backend.enums.UserStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 12/08/2026
 */
public class UserSpecification {
    public static Specification<User> hasStatus(UserStatus status){
        return((root, query, criteriaBuilder) -> {
            if(status == null ) return criteriaBuilder.conjunction();

            return criteriaBuilder.equal(root.get("status"),status);
        });
    }

    public static Specification<User> hasRole(Role role){
        return((root, query, criteriaBuilder) -> {
            if(role == null ) return criteriaBuilder.conjunction();

            return criteriaBuilder.equal(root.get("role"),role);
        });
    }
}
