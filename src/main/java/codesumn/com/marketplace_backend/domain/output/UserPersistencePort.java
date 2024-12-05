package codesumn.com.marketplace_backend.domain.output;

import codesumn.com.marketplace_backend.domain.models.UserModel;

import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {
    Optional<UserModel> findByEmail(String email);

    UserModel save(UserModel user);

    void deleteById(UUID id);

    Optional<UserModel> findById(UUID id);
}