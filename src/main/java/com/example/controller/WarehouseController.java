package com.example.controller;

import com.example.model.Warehouse;
import com.example.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @GetMapping("/{id}")
    @Transactional
    public Optional<Warehouse> getWarehouseWithProducts(@PathVariable Long id) {
        return warehouseRepository.findById(id);
    }

    @GetMapping
    @Transactional
    public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        warehouses.forEach(warehouse -> warehouse.getProducts().size()); // Lazy Loading fix
        return warehouses;
    }
}
