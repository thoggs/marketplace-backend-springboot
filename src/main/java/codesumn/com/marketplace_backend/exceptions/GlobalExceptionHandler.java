package codesumn.com.marketplace_backend.exceptions;

import codesumn.com.marketplace_backend.dtos.ErrorResponseDto;
import codesumn.com.marketplace_backend.dtos.MetadataDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAllExceptions(Exception ex) {
        MetadataDto metadata = new MetadataDto(
                List.of("An unexpected error occurred: " + ex.getMessage())
        );
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAuthenticationException() {
        MetadataDto metadata = new MetadataDto(List.of("Invalid credentials"));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAccessDeniedException(AccessDeniedException ex) {
        MetadataDto metadata = new MetadataDto(List.of(new String[]{"Access Denied: " + ex.getMessage()}));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        MetadataDto metadata = new MetadataDto(List.of("Error: " + ex.getMessage()));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleEmailAlreadyExistsException() {
        MetadataDto metadata = new MetadataDto(List.of("Email is already taken"));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        List<String> errorMessages = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        MetadataDto metadata = new MetadataDto(errorMessages);
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
        MetadataDto metadata = new MetadataDto(List.of("Failed to read request: " + ex.getMessage()));
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
        MetadataDto metadata = new MetadataDto(List.of("Method Not Allowed: " + ex.getMessage()));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
