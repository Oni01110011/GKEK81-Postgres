package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "warehouse") // Stellt sicher, dass es mit der bestehenden DB-Tabelle übereinstimmt
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouseID")
    private Long warehouseID;

    @Column(name = "warehouseName", nullable = false)
    private String warehouseName;

    @Column(name = "warehouseAddress", nullable = false)
    private String warehouseAddress;

    @Column(name = "warehousePostalCode", nullable = false)
    private String warehousePostalCode;

    @Column(name = "warehouseCity", nullable = false)
    private String warehouseCity;

    @Column(name = "warehouseCountry", nullable = false)
    private String warehouseCountry;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;

    // Getter und Setter
    public Long getWarehouseID() { return warehouseID; }
    public void setWarehouseID(Long warehouseID) { this.warehouseID = warehouseID; }

    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

    public String getWarehouseAddress() { return warehouseAddress; }
    public void setWarehouseAddress(String warehouseAddress) { this.warehouseAddress = warehouseAddress; }

    public String getWarehousePostalCode() { return warehousePostalCode; }
    public void setWarehousePostalCode(String warehousePostalCode) { this.warehousePostalCode = warehousePostalCode; }

    public String getWarehouseCity() { return warehouseCity; }
    public void setWarehouseCity(String warehouseCity) { this.warehouseCity = warehouseCity; }

    public String getWarehouseCountry() { return warehouseCountry; }
    public void setWarehouseCountry(String warehouseCountry) { this.warehouseCountry = warehouseCountry; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
