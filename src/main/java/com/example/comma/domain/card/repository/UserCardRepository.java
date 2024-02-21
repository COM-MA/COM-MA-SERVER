package com.example.comma.domain.card.repository;

import com.example.comma.domain.card.entity.Card;
import com.example.comma.domain.card.entity.UserCard;
import com.example.comma.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    List<UserCard> findUserCardByUserIdOrderByCreateDateDesc(Long userId);


    List<UserCard> findUserCardByUserIdOrderByCardNameAsc(Long userId);

    Optional<UserCard> findUserCardByUserIdAndCardId(Long userId, Long cardId);

    List<UserCard> findUserCardByUserIdAndCardIdNot(Long id, Long id1);

    boolean existsByUserAndCard(User user, Card card);

    List<UserCard> findTop5ByUserIdOrderByCreateDateDesc(Long userId);

    List<UserCard> findByUser(User user);

    Optional<Object> findUserCardByUserIdAndId(Long userId, Long userCardId);
}
