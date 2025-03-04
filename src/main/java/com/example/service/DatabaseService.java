package com.example.service;

import com.example.model.Product;
import com.example.model.Warehouse;
import com.example.repository.ProductRepository;
import com.example.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DatabaseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public ApplicationRunner initializeDatabase() {
        return args -> {
            Warehouse warehouse1 = new Warehouse();
            warehouse1.setWarehouseName("Warehouse A");
            warehouse1.setWarehouseAddress("123 Street");
            warehouse1.setWarehousePostalCode("12345");
            warehouse1.setWarehouseCity("Berlin");
            warehouse1.setWarehouseCountry("Germany");
            warehouse1.setTimestamp(LocalDateTime.now());

            warehouse1 = warehouseRepository.save(warehouse1);

            Product product1 = new Product();
            product1.setProductID("P001");
            product1.setProductName("Laptop");
            product1.setProductCategory("Electronics"); // ✅ Kategorie hinzugefügt
            product1.setProductQuantity(10);
            product1.setProductUnit("pcs");
            product1.setWarehouse(warehouse1);

            Product product2 = new Product();
            product2.setProductID("P002");
            product2.setProductName("Smartphone");
            product2.setProductCategory("Electronics"); // ✅ Kategorie hinzugefügt
            product2.setProductQuantity(5);
            product2.setProductUnit("pcs");
            product2.setWarehouse(warehouse1);

            productRepository.saveAll(Arrays.asList(product1, product2));
        };
    }

}
