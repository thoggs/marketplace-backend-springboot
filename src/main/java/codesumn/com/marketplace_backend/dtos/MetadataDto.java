package codesumn.com.marketplace_backend.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record MetadataDto(@JsonProperty("messages") String[] messages) implements Serializable {

    @JsonCreator
    public MetadataDto {
    }

    @Override
    public String[] messages() {
        return messages;
    }
}
