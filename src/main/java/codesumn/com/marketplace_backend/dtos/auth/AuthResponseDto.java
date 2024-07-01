package codesumn.com.marketplace_backend.dtos.auth;

import codesumn.com.marketplace_backend.dtos.record.AuthUserResponseRecordDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDto(
        @JsonProperty("user") AuthUserResponseRecordDto user,
        @JsonProperty("accessToken") String accessToken) {

    @JsonCreator
    public AuthResponseDto {}
}
