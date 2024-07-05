package codesumn.com.marketplace_backend.dtos.record;

import codesumn.com.marketplace_backend.shared.enums.RolesEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRecordDto(
        @NotNull UUID id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotNull RolesEnum role
) {
}
