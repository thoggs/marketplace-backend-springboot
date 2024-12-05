package codesumn.com.marketplace_backend.application.dtos.auth;

import codesumn.com.marketplace_backend.application.dtos.record.AuthUserResponseRecordDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDto(
        @JsonProperty("user") AuthUserResponseRecordDto user,
        @JsonProperty("accessToken") String accessToken) {

    @JsonCreator
    public AuthResponseDto {}
}
