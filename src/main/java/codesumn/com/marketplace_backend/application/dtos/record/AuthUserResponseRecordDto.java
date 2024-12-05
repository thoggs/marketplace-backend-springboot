package codesumn.com.marketplace_backend.application.dtos.record;

import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.shared.enums.RolesEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AuthUserResponseRecordDto(
        @NotBlank UUID id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotNull RolesEnum role
) {
    public AuthUserResponseRecordDto(UserModel user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }
}
