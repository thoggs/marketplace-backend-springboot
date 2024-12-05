package codesumn.com.marketplace_backend.application.dtos.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecordDto(
        @NotBlank UUID id,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String category,
        @NotNull BigDecimal price,
        @NotBlank String image,
        @NotNull int stock
) {
}
