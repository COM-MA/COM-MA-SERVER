package com.example.comma.domain.fairytale.repository;

import com.example.comma.domain.fairytale.entity.Fairytale;
import com.example.comma.domain.fairytale.entity.UserFairytale;
import com.example.comma.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFairytaleRepository extends JpaRepository<UserFairytale, Long> {
    UserFairytale findByUserAndFairytale(User user, Fairytale fairytale);

    List<UserFairytale> findByUser(User user);
}
