package com.example.comma.domain.card.repository;

import com.example.comma.domain.card.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    List<UserCard> findUserCardByUserIdOrderByCreateDateDesc(Long userId);

    List<UserCard> findUserCardByUserIdAndCardNameContainingOrderByCreateDateDesc(Long userId, String alphabet);

    List<UserCard> findUserCardByUserIdOrderByCardNameAsc(Long userId);

    Optional<Object> findUserCardByUserIdAndCardId(Long userId, Long cardId);
}
