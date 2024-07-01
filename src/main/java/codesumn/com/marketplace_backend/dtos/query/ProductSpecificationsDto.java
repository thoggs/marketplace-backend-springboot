package codesumn.com.marketplace_backend.dtos.query;

import codesumn.com.marketplace_backend.app.models.ProductModel;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecificationsDto {

    public static Specification<ProductModel> searchWithTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), likePattern)
            );
        };
    }
}
