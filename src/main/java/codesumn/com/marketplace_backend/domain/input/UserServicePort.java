package codesumn.com.marketplace_backend.domain.input;

import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserServicePort {
    PaginationResponseDto<List<UserRecordDto>> getUsers(
            int page,
            int pageSize,
            String searchTerm,
            String sorting
    ) throws IOException;

    ResponseDto<UserRecordDto> getUserById(UUID id);

    ResponseDto<UserRecordDto> createUser(UserInputRecordDto userInput);

    ResponseDto<UserRecordDto> updateUser(UUID id, UserInputRecordDto userInput);

    ResponseDto<UserRecordDto> deleteUser(UUID id);
}
