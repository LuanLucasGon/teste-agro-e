package com.example.teste_agro_e.repositories;

import com.example.teste_agro_e.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    Optional<Truck> findBylicensePlate(String licensePlate);
}
