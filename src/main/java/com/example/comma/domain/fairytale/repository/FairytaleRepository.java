package com.example.comma.domain.fairytale.repository;

import com.example.comma.domain.fairytale.entity.Fairytale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FairytaleRepository extends JpaRepository<Fairytale, Long> {
}
