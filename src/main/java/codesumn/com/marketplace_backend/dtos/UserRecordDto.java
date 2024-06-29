package codesumn.com.marketplace_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String role
) {
}
