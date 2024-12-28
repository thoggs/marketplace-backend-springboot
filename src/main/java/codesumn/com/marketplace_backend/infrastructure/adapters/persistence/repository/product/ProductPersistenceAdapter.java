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

    private final ProductJpaRepository productJpaRepository;

    public ProductPersistenceAdapter(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Page<ProductModel> findAllWithSpecAndPageable(Specification<ProductModel> specification, Pageable pageable) {
        return productJpaRepository.findAll(specification, pageable);
    }

    @Override
    public Page<ProductModel> findAllWithPageable(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductModel> findById(UUID id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public void save(ProductModel productModel) {
        productJpaRepository.save(productModel);
    }

    @Override
    public void delete(ProductModel productModel) {
        productJpaRepository.delete(productModel);
    }
}
