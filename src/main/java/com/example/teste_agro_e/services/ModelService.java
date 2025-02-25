package com.example.teste_agro_e.services;

import com.example.teste_agro_e.domain.Brand;
import com.example.teste_agro_e.domain.Model;
import com.example.teste_agro_e.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private FipeService fipeService;

    public List<Model> getAllAvaliableModels(String brandCode){
        this.fipeService.catchModels(brandCode);
        return this.modelRepository.findAll();
    }
}
