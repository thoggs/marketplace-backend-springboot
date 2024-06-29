package codesumn.com.marketplace_backend.repository;

import codesumn.com.marketplace_backend.app.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByEmail(String email);
}

