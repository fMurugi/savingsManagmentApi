package com.fiona.savingsManagementApi.Exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ExceptionResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timeStamp;

    public ExceptionResponse(String message, HttpStatus httpStatus, LocalDateTime now) {
        this.message = message;
        this.status = httpStatus;
        this.timeStamp = now;
    }

}
