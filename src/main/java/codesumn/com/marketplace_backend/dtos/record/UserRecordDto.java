package codesumn.com.marketplace_backend.dtos.record;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserRecordDto(
        @NotBlank UUID id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String role
) {
}
