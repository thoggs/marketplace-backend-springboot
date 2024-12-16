package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository;

import codesumn.com.marketplace_backend.domain.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);
}

