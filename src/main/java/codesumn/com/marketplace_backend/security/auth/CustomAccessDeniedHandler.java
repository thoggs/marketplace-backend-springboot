package codesumn.com.marketplace_backend.security.auth;

import codesumn.com.marketplace_backend.dtos.auth.ErrorResponseDto;
import codesumn.com.marketplace_backend.dtos.response.ErrorMessageDto;
import codesumn.com.marketplace_backend.dtos.record.MetadataRecordDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "ACCESS_DENIED",
                "Access Denied: " + accessDeniedException.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));

        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
    }
}

