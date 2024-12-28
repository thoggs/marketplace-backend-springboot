package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.product;

import codesumn.com.marketplace_backend.domain.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends
        JpaRepository<ProductModel, UUID>, JpaSpecificationExecutor<ProductModel> {
}
