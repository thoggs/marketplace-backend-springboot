package codesumn.com.marketplace_backend.repository;

import codesumn.com.marketplace_backend.app.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
}
