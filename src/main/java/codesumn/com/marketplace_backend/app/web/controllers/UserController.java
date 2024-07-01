package codesumn.com.marketplace_backend.app.web.controllers;

import codesumn.com.marketplace_backend.app.models.UserModel;
import codesumn.com.marketplace_backend.dtos.record.MetadataPaginationRecordDto;
import codesumn.com.marketplace_backend.dtos.record.UserRecordDto;
import codesumn.com.marketplace_backend.dtos.response.PaginationDto;
import codesumn.com.marketplace_backend.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDto<List<UserRecordDto>>> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<UserModel> userPage = userRepository.findAll(pageable);
        List<UserRecordDto> userRecords = userPage.getContent().stream()
                .map(user -> new UserRecordDto(
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
}
