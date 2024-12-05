package codesumn.com.marketplace_backend.domain.models;

import codesumn.com.marketplace_backend.application.dtos.record.ProductInputRecordDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_products")
public class ProductModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "category", nullable = false)
    private String category;

    public ProductModel(ProductInputRecordDto productCreateRecordDto) {
        this.name = productCreateRecordDto.name();
        this.description = productCreateRecordDto.description();
        this.price = productCreateRecordDto.price();
        this.image = productCreateRecordDto.image();
        this.stock = productCreateRecordDto.stock();
        this.category = productCreateRecordDto.category();
    }
}
