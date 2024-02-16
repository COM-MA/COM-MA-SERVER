package com.example.comma.domain.card.repository;

import com.example.comma.domain.card.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
}
