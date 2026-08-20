package com.hoverse.backend.repository;

import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    @Query("select MONTH(u.createdAt), count(u.id) from User u where YEAR(u.createdAt) = :year group by MONTH(u.createdAt)")
    List<Object[]> countUsersGroupByMonth(@Param("year") int year);
}
