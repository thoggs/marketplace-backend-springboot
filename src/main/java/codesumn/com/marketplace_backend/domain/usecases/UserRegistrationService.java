package codesumn.com.marketplace_backend.domain.usecases;

import codesumn.com.marketplace_backend.application.dtos.auth.AuthCredentialsRecordDto;
import codesumn.com.marketplace_backend.application.dtos.auth.AuthResponseDto;
import codesumn.com.marketplace_backend.application.dtos.record.GitHubTokenRequestRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;

public interface UserRegistrationService {
    ResponseDto<AuthResponseDto> authenticateUser(AuthCredentialsRecordDto credentials);

    ResponseDto<AuthResponseDto> authenticateGitHubUser(GitHubTokenRequestRecordDto tokenRequest);

    ResponseDto<AuthResponseDto> registerUser(UserInputRecordDto user);
}
