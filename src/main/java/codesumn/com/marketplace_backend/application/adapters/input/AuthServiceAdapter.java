package codesumn.com.marketplace_backend.application.adapters.input;

import codesumn.com.marketplace_backend.application.dtos.auth.AuthCredentialsRecordDto;
import codesumn.com.marketplace_backend.application.dtos.auth.AuthResponseDto;
import codesumn.com.marketplace_backend.application.dtos.record.AuthUserResponseRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.GitHubTokenRequestRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.GitHubUserDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.application.mappers.UserMapper;
import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.domain.output.UserPersistencePort;
import codesumn.com.marketplace_backend.domain.input.UserRegistrationPort;
import codesumn.com.marketplace_backend.services.jwt.JwtService;
import codesumn.com.marketplace_backend.services.web.GitHubService;
import codesumn.com.marketplace_backend.shared.enums.RolesEnum;
import codesumn.com.marketplace_backend.shared.exceptions.errors.CustomUserNotFoundException;
import codesumn.com.marketplace_backend.shared.exceptions.errors.EmailAlreadyExistsException;
import codesumn.com.marketplace_backend.shared.exceptions.errors.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceAdapter implements UserRegistrationPort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GitHubService gitHubService;

    public AuthServiceAdapter(
            UserPersistencePort userPersistencePort,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager, GitHubService gitHubService
    ) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.gitHubService = gitHubService;
    }

    @Override
    public ResponseDto<AuthResponseDto> authenticateUser(AuthCredentialsRecordDto credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserModel user = userPersistencePort.findByEmail(userDetails.getUsername())
                .orElseThrow(CustomUserNotFoundException::new);
        String token = jwtService.generateToken(userDetails.getUsername());

        AuthUserResponseRecordDto userData = new AuthUserResponseRecordDto(user);
        AuthResponseDto authResponse = new AuthResponseDto(userData, token);

        return ResponseDto.create(authResponse);
    }

    @Override
    public ResponseDto<AuthResponseDto> authenticateGitHubUser(GitHubTokenRequestRecordDto tokenRequest) {
        GitHubUserDto gitHubUser = gitHubService.getGitHubUser(tokenRequest.githubToken());

        UserModel user = new UserModel();

        if (userPersistencePort.existsByEmail(gitHubUser.getEmail())) {
            user = userPersistencePort.findByEmail(gitHubUser.getEmail()).orElseThrow(ResourceNotFoundException::new);
        } else {
            user.setFirstName(gitHubUser.getName());
            user.setLastName("");
            user.setEmail(gitHubUser.getEmail());
            user.setPassword(passwordEncoder.encode(tokenRequest.githubToken()));
            user.setRole(RolesEnum.USER);

            userPersistencePort.saveUser(user);
        }

        String token = jwtService.generateToken(user.getEmail());

        AuthUserResponseRecordDto userData = new AuthUserResponseRecordDto(user);
        AuthResponseDto authResponse = new AuthResponseDto(userData, token);

        return ResponseDto.create(authResponse);
    }

    @Override
    public ResponseDto<AuthResponseDto> registerUser(UserInputRecordDto userInput) {
        if (userPersistencePort.findByEmail(userInput.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        UserModel newUser = UserMapper.fromDto(userInput, passwordEncoder);

        userPersistencePort.saveUser(newUser);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userInput.email(), userInput.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        AuthUserResponseRecordDto userData = new AuthUserResponseRecordDto(newUser);
        AuthResponseDto authResponse = new AuthResponseDto(userData, token);

        return ResponseDto.create(authResponse);
    }
}
