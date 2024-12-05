package codesumn.com.marketplace_backend.application.dtos.response;

import codesumn.com.marketplace_backend.application.dtos.record.MetadataRecordDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public record ResponseDto<T>(
        T data,
        Boolean success,
        List<MetadataRecordDto> metadata
) implements Serializable {

    @JsonCreator
    public ResponseDto(
            @JsonProperty("data") T data,
            @JsonProperty("success") Boolean success,
            @JsonProperty("metadata") List<MetadataRecordDto> metadata) {
        this.data = data;
        this.success = success;
        this.metadata = metadata;
    }

    public static <T> ResponseDto<T> create(
            @JsonProperty("data") T data,
            @JsonProperty("metadata") List<MetadataRecordDto> metadata) {
        if (metadata == null || metadata.isEmpty()) {
            metadata = Collections.singletonList(
                    new MetadataRecordDto(
                            List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
                    )
            );
        }
        return new ResponseDto<>(data, true, metadata);
    }

    public static <T> ResponseDto<T> create(@JsonProperty("data") T data) {
        return create(data, null);
    }

    public static ResponseDto<List<Object>> createWithoutData(List<MetadataRecordDto> metadata) {
        if (metadata == null || metadata.isEmpty()) {
            metadata = Collections.singletonList(new MetadataRecordDto(
                    List.of(new ErrorMessageDto("INFO", "Operation completed successfully.", null))
            ));
        }
        return new ResponseDto<>(Collections.emptyList(), true, metadata);
    }

    public static ResponseDto<List<Object>> createWithoutData() {
        return createWithoutData(null);
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "data=" + data +
                ", success=" + success +
                ", metadata=" + metadata +
                '}';
    }
}