package codesumn.com.marketplace_backend.application.controllers;

import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping("/check")
    public ResponseEntity<ResponseDto<List<Object>>> health() {
        ResponseDto<List<Object>> response = ResponseDto.createWithoutData();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

