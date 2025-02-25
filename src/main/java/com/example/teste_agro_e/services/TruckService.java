package com.example.teste_agro_e.services;

import com.example.teste_agro_e.domain.*;
import com.example.teste_agro_e.dtos.TruckDTO;
import com.example.teste_agro_e.repositories.*;
import com.example.teste_agro_e.shared.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private FipeService fipeService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private YearsRepository yearsRepository;

    public Truck createNewTruck(TruckDTO data) {
        return saveOrUpdateTruck(null, data);
    }

    public Truck updateTruck(Long id, TruckDTO data) {
        return saveOrUpdateTruck(id, data);
    }

    private Truck saveOrUpdateTruck(Long id, TruckDTO data) {
        try {
            fipeService.catchBrands();
            Brand brand = brandRepository.findByName(data.brand()).orElseThrow(() -> new CustomException("BRAND_NOT_FOUND", "Marca informada não registrada na tabela FIPE."));

            fipeService.catchModels(brand.getCode());
            Model model = modelRepository.findByName(data.model()).orElseThrow(() -> new CustomException("MODEL_NOT_FOUND", "Modelo informado não registrado na tabela FIPE."));

            fipeService.catchYears(brand.getCode(), model.getCode());
            Years years = yearsRepository.findByName(data.manufacturingYear()).orElseThrow(() -> new CustomException("YEAR_NOT_FOUND", "Ano informado não registrado na tabela FIPE."));

            truckRepository.findBylicensePlate(data.licensePlate()).ifPresent(t -> { throw new CustomException("LICENSE_PLATE_DUPLICATE", "Já existe um veículo com esta placa registrada."); });

            Double price = fipeService.getFipePrice(brand.getCode(), model.getCode(), years.getCode());

            Truck truck = (id != null) ? truckRepository.findById(id)
                    .orElseThrow(() -> new CustomException("TRUCK_NOT_FOUND", "Veículo não registrado na base.")) : new Truck(data);

            truck.setFipePrice(price);
            truck.setBrand(data.brand());
            truck.setModel(data.model());
            truck.setLicensePlate(data.licensePlate());
            truck.setManufacturingYear(data.manufacturingYear());

            return truckRepository.save(truck);
        } catch (CustomException ex) {
            throw ex; // Rethrow CustomException to be handled by a global exception handler.
        } catch (Exception ex) {
            throw new CustomException("GENERAL_ERROR", "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
        }
    }

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }
}
