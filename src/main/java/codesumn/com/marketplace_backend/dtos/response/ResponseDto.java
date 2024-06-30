package codesumn.com.marketplace_backend.dtos.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AutoValue
public abstract class ResponseDto<T> implements Serializable {

    @JsonProperty("data")
    public abstract Optional<T> data();

    @JsonProperty("success")
    public abstract Boolean success();

    @JsonProperty("metadata")
    public abstract List<MetadataDto> metadata();

    @JsonCreator
    public static <T> ResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("metadata") List<MetadataDto> metadata
    ) {
        if (metadata == null || metadata.isEmpty()) {
            metadata = Collections.singletonList(
                    new MetadataDto(List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null)))
            );
        }
        return new AutoValue_ResponseDto<>(Optional.ofNullable(data), true, metadata);
    }

    public static <T> ResponseDto<T> create(
            @JsonProperty("data") T data
    ) {
        return create(data, null);
    }

    public static ResponseDto<List<Object>> createWithoutData(List<MetadataDto> metadata) {
        if (metadata == null || metadata.isEmpty()) {
            metadata = Collections.singletonList(new MetadataDto(
                    List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
            ));
        }
        return new AutoValue_ResponseDto<>(Optional.of(Collections.emptyList()), true, metadata);
    }

    public static ResponseDto<List<Object>> createWithoutData() {
        return createWithoutData(null);
    }
}
