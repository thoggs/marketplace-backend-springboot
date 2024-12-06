package codesumn.com.marketplace_backend.application.services;

import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.UserRepository;
import codesumn.com.marketplace_backend.shared.enums.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCreationService {

    private final UserRepository userRepository;

    @Autowired
    public UserCreationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel saveUser(UserInputRecordDto userInput) {
        UserModel userModel = new UserModel(userInput);
        userModel.setRole(RolesEnum.fromValue(userInput.role()));
        return userRepository.save(userModel);
    }
}
