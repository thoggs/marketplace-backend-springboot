package codesumn.com.marketplace_backend.application.dtos.record;

import codesumn.com.marketplace_backend.application.dtos.response.ErrorMessageDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public record MetadataPaginationRecordDto(
        @JsonProperty("pagination") PaginationDto pagination,
        @JsonProperty("messages") List<ErrorMessageDto> messages
        ) implements Serializable {

    @JsonCreator
    public MetadataPaginationRecordDto {
    }
}
