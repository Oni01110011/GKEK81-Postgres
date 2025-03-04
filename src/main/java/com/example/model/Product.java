package com.example.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product") // Stellt sicher, dass es mit der bestehenden DB-Tabelle übereinstimmt
public class Product {
    @Id
    @Column(name = "productID")
    private String productID;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "productCategory", nullable = false)
    private String productCategory;

    @Column(name = "productQuantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "productUnit", nullable = false)
    private String productUnit;

    @ManyToOne
    @JoinColumn(name = "warehouseID")
    @JsonIgnore // Verhindert unendliche Schleifen in der JSON-Ausgabe
    private Warehouse warehouse;

    // Getter und Setter
    public String getProductID() { return productID; }
    public void setProductID(String productID) { this.productID = productID; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    public Integer getProductQuantity() { return productQuantity; }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }

    public String getProductUnit() { return productUnit; }
    public void setProductUnit(String productUnit) { this.productUnit = productUnit; }

    public Warehouse getWarehouse() { return warehouse; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }
}
