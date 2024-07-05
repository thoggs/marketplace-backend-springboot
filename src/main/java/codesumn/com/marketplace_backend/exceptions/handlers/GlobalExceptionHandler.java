package codesumn.com.marketplace_backend.exceptions.handlers;

import codesumn.com.marketplace_backend.dtos.auth.ErrorResponseDto;
import codesumn.com.marketplace_backend.dtos.response.ErrorMessageDto;
import codesumn.com.marketplace_backend.dtos.record.MetadataRecordDto;
import codesumn.com.marketplace_backend.exceptions.errors.EmailAlreadyExistsException;
import codesumn.com.marketplace_backend.exceptions.errors.EnumValidationException;
import codesumn.com.marketplace_backend.exceptions.errors.ResourceNotFoundException;
import codesumn.com.marketplace_backend.exceptions.errors.CustomUnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAllExceptions(Exception ex) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred: " + ex.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(
                Collections.singletonList(errorMessage)
        );
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAuthenticationException() {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "AUTH_ERROR",
                "Invalid credentials",
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleUnauthorizedException(
            CustomUnauthorizedException ex
    ) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "AUTH_ERROR",
                ex.getMessage(),
                "githubToken"
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "ACCESS_DENIED",
                "Access Denied: " + ex.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EnumValidationException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleEnumValidationException(
            EnumValidationException ex
    ) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "ENUM_VALIDATION_ERROR",
                String.format("Invalid value '%s' for enum %s", ex.getInvalidValue(), ex.getEnumName()),
                "role"
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "ILLEGAL_ARGUMENT",
                "Error: " + ex.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleEmailAlreadyExistsException() {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "EMAIL_ALREADY_EXISTS",
                "Email is already taken",
                "email"
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleResourceNotFoundException() {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "RESOURCE_NOT_FOUND",
                "Resource not found in the database",
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleUsernameNotFoundException(
            UsernameNotFoundException ex
    ) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "USER_NOT_FOUND",
                ex.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex
    ) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "BAD_REQUEST",
                "Failed to convert parameter: " + ex.getName() + " with value: " + ex.getValue(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        List<ErrorMessageDto> errorMessages = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorMessageDto(
                        "VALIDATION_ERROR",
                        fieldError.getDefaultMessage(),
                        fieldError.getField()))
                .toList();
        MetadataRecordDto metadata = new MetadataRecordDto(errorMessages);
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "BAD_REQUEST",
                "Failed to read request: " + ex.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "METHOD_NOT_ALLOWED",
                "Method Not Allowed: " + ex.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}