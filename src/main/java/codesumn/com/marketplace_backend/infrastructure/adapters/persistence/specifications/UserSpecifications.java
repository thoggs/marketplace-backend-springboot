package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.specifications;

import codesumn.com.marketplace_backend.domain.models.UserModel;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<UserModel> searchWithTerm(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern)
            );
        };
    }
}

