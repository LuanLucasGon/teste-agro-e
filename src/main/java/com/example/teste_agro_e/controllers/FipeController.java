package com.example.teste_agro_e.controllers;

import com.example.teste_agro_e.domain.Brand;
import com.example.teste_agro_e.domain.Model;
import com.example.teste_agro_e.domain.Years;
import com.example.teste_agro_e.services.BrandService;
import com.example.teste_agro_e.services.FipeService;
import com.example.teste_agro_e.services.ModelService;
import com.example.teste_agro_e.services.YearsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/avaliable")
public class FipeController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private YearsService yearsService;

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getBrands(){
        List<Brand> avaliableBrands =this.brandService.getAllAvaliableBrands();
        return new ResponseEntity<>(avaliableBrands, HttpStatus.OK);
    }

    @GetMapping("/models/{brandCode}")
    public ResponseEntity<List<Model>> getModels(@PathVariable String brandCode) {
        List<Model> avaliableModels = this.modelService.getAllAvaliableModels(brandCode);
        return new ResponseEntity<>(avaliableModels, HttpStatus.OK);
    }

    @GetMapping("/years/{brandCode}/{modelCode}")
    public ResponseEntity<List<Years>> getYears(@PathVariable String brandCode, @PathVariable String modelCode) {
        List<Years> avaliableYears = this.yearsService.getAllAvaliableYears(brandCode, modelCode);
        return new ResponseEntity<>(avaliableYears, HttpStatus.OK);
    }
}
