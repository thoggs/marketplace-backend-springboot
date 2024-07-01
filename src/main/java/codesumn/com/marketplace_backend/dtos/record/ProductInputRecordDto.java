package codesumn.com.marketplace_backend.dtos.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductInputRecordDto(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String category,
        @NotNull BigDecimal price,
        @NotBlank String image,
        @NotNull int stock
) {
}
