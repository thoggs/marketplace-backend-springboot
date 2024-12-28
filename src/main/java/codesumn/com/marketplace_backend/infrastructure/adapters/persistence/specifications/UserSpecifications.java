package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.specifications;

import codesumn.com.marketplace_backend.domain.models.UserModel;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<UserModel> searchWithTerm(String searchTerm) {
        return (root, query, cb) -> {
            String likePattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), likePattern),
                    cb.like(cb.lower(root.get("lastName")), likePattern),
                    cb.like(cb.lower(root.get("email")), likePattern)
            );
        };
    }
}
