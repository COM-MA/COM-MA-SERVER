package com.example.comma.domain.fairytale.repository;

import com.example.comma.domain.fairytale.entity.Fairytale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FairytaleRepository extends JpaRepository<Fairytale, Long> {
    List<Fairytale> findByIdNot(Long fairytaleId);
}
