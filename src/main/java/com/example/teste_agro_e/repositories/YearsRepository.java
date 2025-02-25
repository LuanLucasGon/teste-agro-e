package com.example.teste_agro_e.repositories;

import com.example.teste_agro_e.domain.Years;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YearsRepository extends JpaRepository<Years, Long> {
    Optional<Years> findByName(Integer name);
    Optional<Years> findFirstByBrandCode(String brandCode);
}
