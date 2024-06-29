package codesumn.com.marketplace_backend.exceptions;

import codesumn.com.marketplace_backend.dtos.ErrorResponseDto;
import codesumn.com.marketplace_backend.dtos.MetadataDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAllExceptions(Exception ex) {
        MetadataDto metadata = new MetadataDto(new String[]{"An unexpected error occurred: " + ex.getMessage()});
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto<List<Object>>> handleAccessDeniedException(AccessDeniedException ex) {
        MetadataDto metadata = new MetadataDto(new String[]{"Access Denied: " + ex.getMessage()});
        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
