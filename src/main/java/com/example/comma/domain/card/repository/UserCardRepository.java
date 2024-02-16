package com.example.comma.domain.card.repository;

import com.example.comma.domain.card.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    List<UserCard> findUserCardByUserIdOrderByCreateDateDesc(Long userId);
}
