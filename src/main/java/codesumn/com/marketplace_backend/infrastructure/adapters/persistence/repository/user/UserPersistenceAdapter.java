package codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.user;

import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.domain.output.UserPersistencePort;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.specifications.UserSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;

    public UserPersistenceAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Page<UserModel> findAll(String searchTerm, Pageable pageable) {

        Specification<UserModel> spec = (searchTerm != null && !searchTerm.isEmpty())
                ? UserSpecifications.searchWithTerm(searchTerm)
                : null;

        return userJpaRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public void saveUser(UserModel userModel) {
        userJpaRepository.save(userModel);
    }

    @Override
    public void deleteUser(UserModel userModel) {
        userJpaRepository.delete(userModel);
    }
}
