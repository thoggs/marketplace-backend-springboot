package codesumn.com.marketplace_backend.application.mappers;

import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.shared.enums.RolesEnum;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    public static UserModel fromDto(UserInputRecordDto dto, PasswordEncoder encoder) {
        UserModel user = new UserModel();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        user.setRole(RolesEnum.fromValue(dto.role()));
        return user;
    }
}