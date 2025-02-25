package com.example.teste_agro_e.services;

import com.example.teste_agro_e.domain.Years;
import com.example.teste_agro_e.repositories.YearsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearsService {
    @Autowired
    private YearsRepository yearsRepository;

    @Autowired
    private FipeService fipeService;

    public List<Years> getAllAvaliableYears(String brandCode, String modelCode){
        this.fipeService.catchYears(brandCode, modelCode);
        return this.yearsRepository.findAll();
    }
}
