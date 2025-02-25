package com.example.teste_agro_e.controllers;

import com.example.teste_agro_e.domain.Model;
import com.example.teste_agro_e.domain.Truck;
import com.example.teste_agro_e.dtos.TruckDTO;
import com.example.teste_agro_e.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trucks")
public class TruckController {
    @Autowired
    private TruckService truckService;

    @PostMapping
    public ResponseEntity<Truck> createUser(@RequestBody TruckDTO truckDTO){
        Truck newTruck = truckService.createNewTruck(truckDTO);
        return new ResponseEntity<>(newTruck, HttpStatus.CREATED);
    }

    @PutMapping("/{TruckId}")
    public ResponseEntity<Truck> updateUser(@PathVariable Long TruckId, @RequestBody TruckDTO truckDTO){
        Truck newTruck = truckService.updateTruck(TruckId, truckDTO);
        return new ResponseEntity<>(newTruck, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        List<Truck> allTrucks = this.truckService.getAllTrucks();
        return new ResponseEntity<>(allTrucks, HttpStatus.OK);
    }
}
