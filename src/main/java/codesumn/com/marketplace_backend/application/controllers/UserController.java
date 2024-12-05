package codesumn.com.marketplace_backend.application.controllers;

import codesumn.com.marketplace_backend.application.dtos.params.UserFilterCriteriaParamDTO;
import codesumn.com.marketplace_backend.application.dtos.record.UserInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.UserRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.domain.usecases.UserService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDto<List<UserRecordDto>>> index(
            @Valid @ParameterObject @ModelAttribute UserFilterCriteriaParamDTO parameters
    ) throws IOException {
        return new ResponseEntity<>(userService.getUsers(
                parameters.getPage(),
                parameters.getPageSize(),
                parameters.getSearchTerm(),
                parameters.getSortField()
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserRecordDto>> show(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<UserRecordDto>> store(
            @RequestBody @Valid UserInputRecordDto userInputRecordDto
    ) {
        return new ResponseEntity<>(userService.createUser(userInputRecordDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UserRecordDto>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UserInputRecordDto userInputRecordDto
    ) {
        return new ResponseEntity<>(userService.updateUser(id, userInputRecordDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<UserRecordDto>> destroy(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
}
