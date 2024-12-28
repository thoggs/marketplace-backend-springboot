package codesumn.com.marketplace_backend.domain.output;

import codesumn.com.marketplace_backend.domain.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductPersistencePort {
    Page<ProductModel> findAll(String searchTerm, Pageable pageable);

    Optional<ProductModel> findById(UUID id);

    void save(ProductModel productModel);

    void delete(ProductModel productModel);
}
