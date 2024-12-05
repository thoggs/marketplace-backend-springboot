package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository;

import codesumn.com.marketplace_backend.domain.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID>, JpaSpecificationExecutor<ProductModel> {
}
