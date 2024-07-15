package codesumn.com.marketplace_backend.dtos.response;

import codesumn.com.marketplace_backend.dtos.record.MetadataPaginationRecordDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record PaginationResponseDto<T>(
        T data,
        Boolean success,
        MetadataPaginationRecordDto metadata
) implements Serializable {

    @JsonCreator
    public PaginationResponseDto(
            @JsonProperty("data") T data,
            @JsonProperty("success") Boolean success,
            @JsonProperty("metadata") MetadataPaginationRecordDto metadata) {
        this.data = data;
        this.success = success;
        this.metadata = metadata;
    }

    public static <T> PaginationResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("metadata") MetadataPaginationRecordDto metadata) {
        if (metadata == null || metadata.messages().isEmpty()) {
            assert metadata != null;
            metadata = new MetadataPaginationRecordDto(
                    metadata.pagination(),
                    List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
            );
        }
        return new PaginationResponseDto<>(data, true, metadata);
    }

    public static <T> PaginationResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("pagination") PaginationDto pagination) {
        MetadataPaginationRecordDto metadata = new MetadataPaginationRecordDto(
                pagination,
                List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
        );
        return create(data, metadata);
    }

    @Override
    public String toString() {
        return "PaginationResponseDto{" +
                "data=" + data +
                ", success=" + success +
                ", metadata=" + metadata +
                '}';
    }
}