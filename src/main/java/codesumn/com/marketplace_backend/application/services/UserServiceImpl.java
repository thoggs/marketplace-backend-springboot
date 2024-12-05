package codesumn.com.marketplace_backend.application.services;

import codesumn.com.marketplace_backend.application.dtos.query.UserSpecificationsDto;
import codesumn.com.marketplace_backend.application.dtos.record.MetadataPaginationRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.domain.models.UserModel;
import codesumn.com.marketplace_backend.domain.usecases.UserService;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.repository.UserRepository;
import codesumn.com.marketplace_backend.shared.exceptions.errors.EmailAlreadyExistsException;
import codesumn.com.marketplace_backend.shared.exceptions.errors.ResourceNotFoundException;
import codesumn.com.marketplace_backend.shared.parsers.SortParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SortParser sortParser;
    private final UserCreationService userCreationService;


    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SortParser sortParser,
            UserCreationService userCreationService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sortParser = sortParser;
        this.userCreationService = userCreationService;
    }

    @Override
    public PaginationResponseDto<List<UserRecordDto>> getUsers(
            int page,
            int pageSize,
            String searchTerm,
            String sorting
    ) throws IOException {
        String decodedSorting = (sorting != null && !sorting.trim().isEmpty() && !"[]".equals(sorting))
                ? URLDecoder.decode(sorting, StandardCharsets.UTF_8)
                : null;

        Sort sort = (decodedSorting != null && !decodedSorting.trim().isEmpty() && !"[]".equals(decodedSorting))
                ? sortParser.parseSorting(decodedSorting)
                : Sort.by(Sort.Order.asc("firstName"));

        org.springframework.data.domain.Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

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

        return PaginationResponseDto.create(userRecords, metadata);
    }

    @Override
    public ResponseDto<UserRecordDto> getUserById(UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        UserRecordDto userRecord = new UserRecordDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseDto.create(userRecord);
    }

    @Override
    public ResponseDto<UserRecordDto> createUser(UserInputRecordDto userInput) {
        userRepository
                .findByEmail(userInput.email())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException();
                });

        UserModel newUser = userCreationService.saveUser(userInput);

        newUser.setPassword(passwordEncoder.encode(userInput.password()));

        userRepository.save(newUser);

        UserRecordDto newUserRecord = new UserRecordDto(
                newUser.getId(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getEmail(),
                newUser.getRole()
        );

        return ResponseDto.create(newUserRecord);
    }

    @Override
    public ResponseDto<UserRecordDto> updateUser(UUID id, UserInputRecordDto userInput) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        userRepository
                .findByEmail(userInput.email())
                .ifPresent(user -> {
                    if (!user.getEmail().equals(existingUser.getEmail())) {
                        throw new EmailAlreadyExistsException();
                    }
                });

        existingUser.setFirstName(userInput.firstName());
        existingUser.setLastName(userInput.lastName());
        existingUser.setEmail(userInput.email());
        existingUser.setRole(userInput.role());

        if (!userInput.password().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userInput.password()));
        }

        userRepository.save(existingUser);

        UserRecordDto updatedUserRecord = new UserRecordDto(
                existingUser.getId(),
                existingUser.getFirstName(),
                existingUser.getLastName(),
                existingUser.getEmail(),
                existingUser.getRole()
        );

        return ResponseDto.create(updatedUserRecord);
    }

    @Override
    public ResponseDto<UserRecordDto> deleteUser(UUID id) {
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

        return ResponseDto.create(userRecord);
    }

}
