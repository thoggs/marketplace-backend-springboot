package codesumn.com.marketplace_backend.app.web.controllers;

import codesumn.com.marketplace_backend.app.models.UserModel;
import codesumn.com.marketplace_backend.dtos.auth.AuthCredentialsRecordDto;
import codesumn.com.marketplace_backend.dtos.auth.AuthResponseDto;
import codesumn.com.marketplace_backend.dtos.record.AuthSignupUserRecordDto;
import codesumn.com.marketplace_backend.dtos.record.AuthUserResponseRecordDto;
import codesumn.com.marketplace_backend.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.exceptions.EmailAlreadyExistsException;
import codesumn.com.marketplace_backend.repository.UserRepository;
import codesumn.com.marketplace_backend.services.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<AuthResponseDto>> authenticateUser(
            @RequestBody @Valid AuthCredentialsRecordDto credentials
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserModel user = userRepository.findByEmail(userDetails.getUsername());
        String token = jwtService.generateToken(userDetails.getUsername());

        AuthUserResponseRecordDto userData = new AuthUserResponseRecordDto(user);
        AuthResponseDto authResponse = new AuthResponseDto(userData, token);

        ResponseDto<AuthResponseDto> response = ResponseDto.create(authResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<AuthResponseDto>> registerUser(
            @RequestBody @Valid AuthSignupUserRecordDto user
    ) {
        if (userRepository.findByEmail(user.email()) != null) {
            throw new EmailAlreadyExistsException();
        }

        UserModel newUser = new UserModel(user);
        newUser.setPassword(passwordEncoder.encode(user.password()));

        userRepository.save(newUser);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.email(), user.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        AuthUserResponseRecordDto userData = new AuthUserResponseRecordDto(newUser);
        AuthResponseDto authResponse = new AuthResponseDto(userData, token);

        ResponseDto<AuthResponseDto> response = ResponseDto.create(authResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
