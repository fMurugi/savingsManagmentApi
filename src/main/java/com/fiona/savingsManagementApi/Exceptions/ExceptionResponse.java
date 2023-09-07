package com.fiona.savingsManagementApi.Exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timeStamp;

    public ExceptionResponse(String message, HttpStatus httpStatus, LocalDateTime now) {
    }
}
