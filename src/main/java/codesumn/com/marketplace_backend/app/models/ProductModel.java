package codesumn.com.marketplace_backend.app.models;

import codesumn.com.marketplace_backend.dtos.record.ProductInputRecordDto;
import codesumn.com.marketplace_backend.dtos.record.ProductRecordDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "PRODUCTS")
public class ProductModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private int stock;
    private String category;

    public ProductModel() {
    }

    public ProductModel(ProductRecordDto productRecordDto) {
        this.name = productRecordDto.name();
        this.description = productRecordDto.description();
        this.price = productRecordDto.price();
        this.image = productRecordDto.image();
        this.stock = productRecordDto.stock();
        this.category = productRecordDto.category();
    }

    public ProductModel(ProductInputRecordDto productCreateRecordDto) {
        this.name = productCreateRecordDto.name();
        this.description = productCreateRecordDto.description();
        this.price = productCreateRecordDto.price();
        this.image = productCreateRecordDto.image();
        this.stock = productCreateRecordDto.stock();
        this.category = productCreateRecordDto.category();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
