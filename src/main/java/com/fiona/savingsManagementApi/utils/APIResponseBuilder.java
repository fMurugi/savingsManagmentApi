package com.fiona.savingsManagementApi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class APIResponseBuilder {
    public static <T> ResponseEntity<APiResponse> buildResponseEntity(HttpStatus status, T body, String path) {
        APiResponse<T> responseDTO = APiResponse.<T>builder()
                .body(body)
                .timeStamp(LocalDateTime.now())
                .path(path)
                .build();
        return new ResponseEntity<>(responseDTO, status);
    }
}
