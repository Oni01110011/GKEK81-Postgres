# **📜 Protokoll: Implementierung einer Warehouse-Management-API mit Spring Boot 🚀**
**von :** *Bernhard Aichinger-Ganas* <br>
**am :** *04.03.2025* <br>
**Github :** *https://github.com/Oni01110011/GKEK81-Postgres.git*
## **📌 1. Zielsetzung**
Ziel dieser Aufgabe war es, eine **REST-API** mit **Spring Boot** zu entwickeln, die ein **Warehouse-Management-System** bereitstellt.  
Die API sollte es ermöglichen, **Warehouses (Lagerhäuser) und zugehörige Produkte** zu verwalten, Daten über eine Datenbank zu speichern und per **Postman** oder anderen Tools abrufbar zu machen.

---

## **📌 2. Anforderungen & Funktionen**
### **✅ Grundlegende Anforderungen:**
- **Erstellung von Warehouses**
- **Erstellung von Produkten, die einem Warehouse zugeordnet sind**
- **Abrufen einzelner Warehouses mit allen Produkten**
- **Abrufen aller Warehouses**
- **Automatische Initialisierung mit Testdaten**
- **Persistenz über eine relationale Datenbank (MySQL oder PostgreSQL)**

### **📌 Erweiterte Anforderungen (später hinzugefügt & implementiert):**
- **Automatische Datenbankbereinigung & Neuinsertion von Testdaten beim Start**
- **Verhinderung von Endlosschleifen bei JSON-Serialisierung**
- **Fehlermeldungen & Debugging von Hibernate-Exceptions**
- **Gradle-Konfiguration für reibungslose Nutzung**

---

## **📌 3. Projektstruktur**
Die Anwendung wurde in folgende **Spring Boot Pakete & Klassen** unterteilt:

```
📦 src/main/java/com/example
 ┣ 📂 controller
 ┃ ┗ 📜 WarehouseController.java
 ┣ 📂 model
 ┃ ┣ 📜 Product.java
 ┃ ┗ 📜 Warehouse.java
 ┣ 📂 repository
 ┃ ┣ 📜 ProductRepository.java
 ┃ ┗ 📜 WarehouseRepository.java
 ┣ 📂 service
 ┃ ┗ 📜 DatabaseService.java
 ┗ 📜 Application.java
```
---

## **📌 4. Implementierung & Änderungen**
### **🔥 4.1 `Warehouse.java` (Modell für Lagerhäuser)**
**Problem:** Hibernate erstellte `warehouse_name` anstelle von `WarehouseName`.  
**Lösung:** `@Column(name = "WarehouseName")` wurde hinzugefügt, um den exakten Spaltennamen zu setzen.

**Finaler Code:**
```java
@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WarehouseID")
    private Long warehouseID;

    @Column(name = "WarehouseName", nullable = false)
    private String warehouseName;

    @Column(name = "WarehouseAddress", nullable = false)
    private String warehouseAddress;

    @Column(name = "WarehousePostalCode", nullable = false)
    private String warehousePostalCode;

    @Column(name = "WarehouseCity", nullable = false)
    private String warehouseCity;

    @Column(name = "WarehouseCountry", nullable = false)
    private String warehouseCountry;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
}
```

---

### **🔥 4.2 `Product.java` (Modell für Produkte)**
**Problem:** `productCategory` war `null`, was zu einer **`DataIntegrityViolationException`** führte.  
**Lösung:** `productCategory` wurde in `DatabaseService.java` ergänzt.

**Finaler Code:**
```java
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "ProductID")
    private String productID;

    @Column(name = "ProductName", nullable = false)
    private String productName;

    @Column(name = "ProductCategory", nullable = false)
    private String productCategory;

    @Column(name = "ProductQuantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "ProductUnit", nullable = false)
    private String productUnit;

    @ManyToOne
    @JoinColumn(name = "WarehouseID")
    @JsonIgnore
    private Warehouse warehouse;
}
```

---

### **🔥 4.3 `WarehouseController.java` (REST API Controller)**
#### **1️⃣ `POST /warehouses` – Warehouse hinzufügen**
```java
@PostMapping
public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
    return warehouseRepository.save(warehouse);
}
```

#### **2️⃣ `GET /warehouses/{id}` – Warehouse mit Produkten abrufen**
```java
@GetMapping("/{id}")
public ResponseEntity<Warehouse> getWarehouseWithProducts(@PathVariable Long id) {
    return warehouseRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
```

#### **3️⃣ `GET /warehouses` – Alle Warehouses abrufen**
```java
@GetMapping
public List<Warehouse> getAllWarehouses() {
    return warehouseRepository.findAll();
}
```

---

### **🔥 4.4 `DatabaseService.java` (Automatische Datenbankinitialisierung)**
**Problem:** Fehlende Werte für `productCategory` führten zu einer Exception.  
**Lösung:** Standardwerte wurden ergänzt.

**Finaler Code:**
```java
@Bean
public ApplicationRunner initializeDatabase() {
    return args -> {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setWarehouseName("Warehouse A");
        warehouse1.setWarehouseAddress("123 Street");
        warehouse1.setWarehousePostalCode("12345");
        warehouse1.setWarehouseCity("Berlin");
        warehouse1.setWarehouseCountry("Germany");

        warehouse1 = warehouseRepository.save(warehouse1);

        Product product1 = new Product();
        product1.setProductID("P001");
        product1.setProductName("Laptop");
        product1.setProductCategory("Electronics"); // ✅ Kategorie hinzugefügt
        product1.setWarehouse(warehouse1);

        productRepository.save(product1);
    };
}
```

---

### **🔥 4.5 `build.gradle` (Gradle Konfiguration)**
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'mysql:mysql-connector-java:8.0.33'
    implementation 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

---

## **📌 5. API-Test mit Postman**
### **1️⃣ Warehouse hinzufügen**
📌 **POST-Request**
```
POST http://localhost:8080/warehouses
```
📌 **JSON-Body**
```json
{
    "warehouseName": "Warehouse B",
    "warehouseAddress": "456 Avenue",
    "warehousePostalCode": "67890",
    "warehouseCity": "Munich",
    "warehouseCountry": "Germany"
}
```

### **2️⃣ Alle Warehouses abrufen**
📌 **GET-Request**
```
GET http://localhost:8080/warehouses
```
📌 **Erwartete Antwort**
```json
[
    {
        "warehouseID": 1,
        "warehouseName": "Warehouse A",
        "products": [
            {
                "productID": "P001",
                "productName": "Laptop",
                "productCategory": "Electronics"
            }
        ]
    }
]
```

---

## **📌 6. Fazit & Lessons Learned**
✅ **Spring Boot mit Hibernate erfolgreich konfiguriert**  
✅ **REST-API Endpunkte mit Postman getestet**  
✅ **Datenbankprobleme (NULL-Felder) behoben**  
✅ **Lazy-Loading und Endlosschleifen gefixt**
