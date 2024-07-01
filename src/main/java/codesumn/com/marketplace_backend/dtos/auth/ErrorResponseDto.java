package codesumn.com.marketplace_backend.dtos.auth;

import codesumn.com.marketplace_backend.dtos.record.MetadataRecordDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AutoValue
public abstract class ErrorResponseDto<T> implements Serializable {

    @JsonProperty("data")
    public abstract Optional<T> data();

    @JsonProperty("success")
    public abstract Boolean success();

    @JsonProperty("metadata")
    public abstract List<MetadataRecordDto> metadata();

    @JsonCreator
    public static <T> ErrorResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("success") Boolean success,
            @JsonProperty("metadata") List<MetadataRecordDto> metadata
    ) {
        return new AutoValue_ErrorResponseDto<>(Optional.ofNullable(data), success, metadata);
    }

    public static ErrorResponseDto<List<Object>> createWithoutData(List<MetadataRecordDto> metadata) {
        return new AutoValue_ErrorResponseDto<>(Optional.of(Collections.emptyList()), false, metadata);
    }
}