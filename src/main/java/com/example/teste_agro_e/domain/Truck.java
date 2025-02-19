package com.example.teste_agro_e.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Truck {
    private long id;
    private String licensePlate;
    private String brand;
    private String model;
    private Integer manufacturingYear;
    private Double fipePrice;
}
