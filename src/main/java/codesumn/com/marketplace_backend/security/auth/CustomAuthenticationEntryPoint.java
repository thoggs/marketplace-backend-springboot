package codesumn.com.marketplace_backend.security.auth;

import codesumn.com.marketplace_backend.dtos.auth.ErrorResponseDto;
import codesumn.com.marketplace_backend.dtos.response.ErrorMessageDto;
import codesumn.com.marketplace_backend.dtos.record.MetadataRecordDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ErrorMessageDto errorMessage = new ErrorMessageDto(
                "AUTH_ERROR",
                "Unauthorized: " + authException.getMessage(),
                null
        );
        MetadataRecordDto metadata = new MetadataRecordDto(Collections.singletonList(errorMessage));

        ErrorResponseDto<List<Object>> errorResponse = ErrorResponseDto
                .createWithoutData(Collections.singletonList(metadata));

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
    }
}
