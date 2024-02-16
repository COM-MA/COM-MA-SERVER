package com.example.comma.domain.card.repository;

import com.example.comma.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findCardByName(String name);
}
