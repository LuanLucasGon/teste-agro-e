package com.example.teste_agro_e.repositories;

import com.example.teste_agro_e.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findByName(String name);
    Optional<Model> findFirstByBrandCode(String brandCode);
}
