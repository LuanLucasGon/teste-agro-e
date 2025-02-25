package com.example.teste_agro_e.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "years")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Years {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brandCode;
    private String code;
    private Integer name;

}