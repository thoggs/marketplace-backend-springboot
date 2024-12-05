package codesumn.com.marketplace_backend.application.dtos.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessageDto {
    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("field")
    private String field;

    @JsonCreator
    public void ErrorMessage(
            @JsonProperty("errorCode") String errorCode,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("field") String field
    ) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.field = field;
    }

    public ErrorMessageDto(String errorCode, String errorMessage, String field) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.field = field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getField() {
        return field;
    }
}
