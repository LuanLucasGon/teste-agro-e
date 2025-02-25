package com.example.teste_agro_e.domain;

import com.example.teste_agro_e.dtos.TruckDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trucks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String licensePlate;
    private String brand;
    private String model;
    private Integer manufacturingYear;
    private Double fipePrice;

    public Truck(TruckDTO data){
        this.licensePlate = data.licensePlate();
        this.brand = data.brand();
        this.model = data.model();
        this.manufacturingYear = data.manufacturingYear();
    }
}
