package codesumn.com.marketplace_backend.application.controllers;

import codesumn.com.marketplace_backend.application.dtos.auth.AuthCredentialsRecordDto;
import codesumn.com.marketplace_backend.application.dtos.auth.AuthResponseDto;
import codesumn.com.marketplace_backend.application.dtos.record.GitHubTokenRequestRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.domain.input.UserRegistrationPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRegistrationPort userRegistrationPort;

    @Autowired
    public AuthController(UserRegistrationPort userRegistrationPort) {
        this.userRegistrationPort = userRegistrationPort;
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<AuthResponseDto>> authenticateUser(
            @RequestBody @Valid AuthCredentialsRecordDto credentials
    ) {
        return new ResponseEntity<>(userRegistrationPort.authenticateUser(credentials), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<AuthResponseDto>> registerUser(
            @RequestBody @Valid UserInputRecordDto user
    ) {
        return new ResponseEntity<>(userRegistrationPort.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/github-signin")
    public ResponseEntity<ResponseDto<AuthResponseDto>> authenticateGitHubUser(
            @RequestBody @Valid GitHubTokenRequestRecordDto tokenRequest
    ) {
        return new ResponseEntity<>(userRegistrationPort.authenticateGitHubUser(tokenRequest), HttpStatus.OK);
    }
}
