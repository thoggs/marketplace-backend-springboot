package codesumn.com.marketplace_backend.dtos.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record MetadataDto(@JsonProperty("messages") List<ErrorMessageDto> messages) implements Serializable {

    @JsonCreator
    public MetadataDto {
    }

    @Override
    public List<ErrorMessageDto> messages() {
        return messages;
    }
}
