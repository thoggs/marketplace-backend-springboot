package codesumn.com.marketplace_backend.dtos.record;

import codesumn.com.marketplace_backend.app.models.UserModel;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record AuthUserResponseRecordDto(
        @NotBlank UUID id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String role
) {
    public AuthUserResponseRecordDto(UserModel user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }
}
