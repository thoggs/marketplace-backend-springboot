package codesumn.com.marketplace_backend.application.dtos.record;

import codesumn.com.marketplace_backend.application.dtos.response.ErrorMessageDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record MetadataRecordDto(@JsonProperty("messages") List<ErrorMessageDto> messages) implements Serializable {

    @JsonCreator
    public MetadataRecordDto {
    }
}
