package codesumn.com.marketplace_backend.dtos.response;

import codesumn.com.marketplace_backend.dtos.record.MetadataPaginationRecordDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AutoValue
public abstract class PaginationResponseDto<T> implements Serializable {

    @JsonProperty("data")
    public abstract Optional<T> data();

    @JsonProperty("success")
    public abstract Boolean success();

    @JsonProperty("metadata")
    public abstract MetadataPaginationRecordDto metadata();

    @JsonCreator
    public static <T> PaginationResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("metadata") MetadataPaginationRecordDto metadata
    ) {
        if (metadata == null || metadata.messages().isEmpty()) {
            assert metadata != null;
            metadata = new MetadataPaginationRecordDto(
                    metadata.pagination(),
                    List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
            );
        }
        return new AutoValue_PaginationResponseDto<>(Optional.ofNullable(data), true, metadata);
    }

    public static <T> PaginationResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("pagination") PaginationDto pagination
    ) {
        MetadataPaginationRecordDto metadata = new MetadataPaginationRecordDto(
                pagination,
                List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
        );
        return create(data, metadata);
    }

    public static PaginationResponseDto<List<Object>> createWithoutData(MetadataPaginationRecordDto metadata) {
        if (metadata == null || metadata.messages().isEmpty()) {
            assert metadata != null;
            metadata = new MetadataPaginationRecordDto(
                    metadata.pagination(),
                    List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
            );
        }
        return new AutoValue_PaginationResponseDto<>(Optional.of(Collections.emptyList()), true, metadata);
    }

    public static PaginationResponseDto<List<Object>> createWithoutData(PaginationDto pagination) {
        MetadataPaginationRecordDto metadata = new MetadataPaginationRecordDto(
                pagination,
                List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
        );
        return createWithoutData(metadata);
    }
}