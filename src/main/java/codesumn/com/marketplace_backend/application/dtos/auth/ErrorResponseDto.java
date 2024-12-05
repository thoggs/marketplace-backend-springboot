package codesumn.com.marketplace_backend.application.dtos.auth;

import codesumn.com.marketplace_backend.application.dtos.record.MetadataRecordDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public record ErrorResponseDto<T>(T data, Boolean success, List<MetadataRecordDto> metadata) implements Serializable {

    @JsonCreator
    public ErrorResponseDto(
            @JsonProperty("data") T data,
            @JsonProperty("success") Boolean success,
            @JsonProperty("metadata") List<MetadataRecordDto> metadata) {
        this.data = data;
        this.success = success;
        this.metadata = metadata;
    }

    public static <T> ErrorResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("success") Boolean success,
            @JsonProperty("metadata") List<MetadataRecordDto> metadata) {
        return new ErrorResponseDto<>(data, success, metadata);
    }

    public static ErrorResponseDto<List<Object>> createWithoutData(List<MetadataRecordDto> metadata) {
        return new ErrorResponseDto<>(Collections.emptyList(), false, metadata);
    }

    @Override
    public String toString() {
        return "ErrorResponseDto{" +
                "data=" + data +
                ", success=" + success +
                ", metadata=" + metadata +
                '}';
    }
}