package codesumn.com.marketplace_backend.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record MetadataDto(List<String> messages) implements Serializable {

    @JsonCreator
    public MetadataDto(@JsonProperty("messages") List<String> messages) {
        this.messages = messages;
    }

    @Override
    @JsonProperty("messages")
    public List<String> messages() {
        return messages;
    }
}
