package codesumn.com.marketplace_backend.exceptions;

import codesumn.com.marketplace_backend.dtos.auth.ErrorResponseDto;
import codesumn.com.marketplace_backend.dtos.response.ErrorMessageDto;
import codesumn.com.marketplace_backend.dtos.response.MetadataDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
        MetadataDto metadata = new MetadataDto(
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
        MetadataDto metadata = new MetadataDto(Collections.singletonList(errorMessage));
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
        MetadataDto metadata = new MetadataDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "ILLEGAL_ARGUMENT",
                "Error: " + ex.getMessage(),
                null
        );
        MetadataDto metadata = new MetadataDto(Collections.singletonList(errorMessage));
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
        MetadataDto metadata = new MetadataDto(Collections.singletonList(errorMessage));
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
        List<ErrorMessageDto> errorMessages = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorMessageDto(
                        "VALIDATION_ERROR",
                        fieldError.getDefaultMessage(),
                        fieldError.getField()))
                .toList();
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
        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "BAD_REQUEST",
                "Failed to read request: " + ex.getMessage(),
                null
        );
        MetadataDto metadata = new MetadataDto(Collections.singletonList(errorMessage));
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
        MetadataDto metadata = new MetadataDto(Collections.singletonList(errorMessage));
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
