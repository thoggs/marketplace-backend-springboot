package codesumn.com.marketplace_backend.services.user;

import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.UserRepository;
import codesumn.com.marketplace_backend.shared.enums.RolesEnum;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel saveUser(UserInputRecordDto userInputRecordDto) {
        String role = userInputRecordDto.role() != null
                ? userInputRecordDto.role()
                : RolesEnum.USER.getValue();

        UserModel userModel = new UserModel(userInputRecordDto);
        userModel.setRole(role);
        return userRepository.save(userModel);
    }
}
