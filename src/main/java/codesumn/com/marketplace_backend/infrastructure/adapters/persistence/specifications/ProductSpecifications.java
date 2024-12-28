package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.specifications;

import codesumn.com.marketplace_backend.domain.models.ProductModel;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<ProductModel> searchWithTerm(String searchTerm) {
        return (root, query, cb) -> {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern),
                    cb.like(cb.lower(root.get("category")), likePattern)
            );
        };
    }
}
