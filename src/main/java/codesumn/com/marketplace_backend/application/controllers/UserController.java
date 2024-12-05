package codesumn.com.marketplace_backend.application.controllers;

import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.application.dtos.query.UserSpecificationsDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.MetadataPaginationRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.shared.exceptions.errors.EmailAlreadyExistsException;
import codesumn.com.marketplace_backend.shared.exceptions.errors.ResourceNotFoundException;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.UserRepository;
import codesumn.com.marketplace_backend.services.user.UserService;
import codesumn.com.marketplace_backend.shared.parsers.SortParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SortParser sortParser;
    private final UserService userService;

    @Autowired
    public UserController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ObjectMapper objectMapper, UserService userService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sortParser = new SortParser(objectMapper);
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDto<List<UserRecordDto>>> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String sorting
    ) throws IOException {
        String decodedSorting = (sorting != null && !sorting.trim().isEmpty() && !"[]".equals(sorting))
                ? URLDecoder.decode(sorting, StandardCharsets.UTF_8)
                : null;

        Sort sort = (decodedSorting != null && !decodedSorting.trim().isEmpty() && !"[]".equals(decodedSorting))
                ? sortParser.parseSorting(decodedSorting)
                : Sort.by(Sort.Order.asc("firstName"));

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        Specification<UserModel> spec = (searchTerm != null && !searchTerm.isEmpty())
                ? UserSpecificationsDto.searchWithTerm(searchTerm)
                : null;

        Page<UserModel> userPage = (spec != null)
                ? userRepository.findAll(spec, pageable)
                : userRepository.findAll(pageable);

        List<UserRecordDto> userRecords = userPage.getContent().stream()
                .map(user -> new UserRecordDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());

        PaginationDto pagination = new PaginationDto(
                userPage.getNumber() + 1,
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );

        MetadataPaginationRecordDto metadata = new MetadataPaginationRecordDto(
                pagination,
                Collections.emptyList()
        );

        PaginationResponseDto<List<UserRecordDto>> response = PaginationResponseDto.create(userRecords, metadata);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserRecordDto>> show(@PathVariable UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        UserRecordDto userRecord = new UserRecordDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );

        ResponseDto<UserRecordDto> response = ResponseDto.create(userRecord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<UserRecordDto>> store(
            @RequestBody @Valid UserInputRecordDto userInputRecordDto
    ) {
        userRepository
                .findByEmail(userInputRecordDto.email())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException();
                });

        UserModel newUser = userService.saveUser(userInputRecordDto);
        newUser.setPassword(passwordEncoder.encode(userInputRecordDto.password()));
        userRepository.save(newUser);

        UserRecordDto newUserRecord = new UserRecordDto(
                newUser.getId(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getEmail(),
                newUser.getRole()
        );

        ResponseDto<UserRecordDto> response = ResponseDto.create(newUserRecord);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UserRecordDto>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UserInputRecordDto userInputRecordDto
    ) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        userRepository
                .findByEmail(userInputRecordDto.email())
                .ifPresent(user -> {
                    if (!user.getEmail().equals(existingUser.getEmail())) {
                        throw new EmailAlreadyExistsException();
                    }
                });

        existingUser.setFirstName(userInputRecordDto.firstName());
        existingUser.setLastName(userInputRecordDto.lastName());
        existingUser.setEmail(userInputRecordDto.email());
        existingUser.setRole(userInputRecordDto.role());

        if (!userInputRecordDto.password().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userInputRecordDto.password()));
        }

        userRepository.save(existingUser);

        UserRecordDto updatedUserRecord = new UserRecordDto(
                existingUser.getId(),
                existingUser.getFirstName(),
                existingUser.getLastName(),
                existingUser.getEmail(),
                existingUser.getRole()
        );

        ResponseDto<UserRecordDto> response = ResponseDto.create(updatedUserRecord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<UserRecordDto>> destroy(@PathVariable UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        userRepository.delete(user);

        UserRecordDto userRecord = new UserRecordDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );

        ResponseDto<UserRecordDto> response = ResponseDto.create(userRecord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}