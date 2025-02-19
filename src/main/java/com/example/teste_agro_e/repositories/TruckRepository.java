package com.example.teste_agro_e.repositories;

import com.example.teste_agro_e.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Long> {
}
