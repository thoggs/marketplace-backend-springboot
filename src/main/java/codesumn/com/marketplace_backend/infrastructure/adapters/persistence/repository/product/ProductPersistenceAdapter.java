package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.product;

import codesumn.com.marketplace_backend.domain.models.ProductModel;
import codesumn.com.marketplace_backend.domain.output.ProductPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductPersistenceAdapter implements ProductPersistencePort {
    @Override
    public Page<ProductModel> findAllWithSpecAndPageable(Specification<ProductModel> specification, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProductModel> findAllWithPageable(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<ProductModel> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void save(ProductModel productModel) {

    }

    @Override
    public void delete(ProductModel productModel) {

    }
}
