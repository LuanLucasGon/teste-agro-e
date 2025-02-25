package com.example.teste_agro_e.services;

import ch.qos.logback.core.model.Model;
import com.example.teste_agro_e.domain.Brand;
import com.example.teste_agro_e.dtos.BrandDTO;
import com.example.teste_agro_e.repositories.BrandRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllAvaliableBrands(){
        return this.brandRepository.findAll();
    }

}
