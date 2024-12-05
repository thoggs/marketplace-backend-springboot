package codesumn.com.marketplace_backend.application.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthCredentialsRecordDto(
        @NotBlank String email,
        @NotBlank String password
) {
}
