package codesumn.com.marketplace_backend.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDto(
        @JsonProperty("user") AuthUserRecordDto user,
        @JsonProperty("accessToken") String accessToken) {

    @JsonCreator
    public AuthResponseDto {}
}
