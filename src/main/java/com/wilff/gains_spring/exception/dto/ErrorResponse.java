package com.wilff.gains_spring.exception.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    
    private String message;
    private LocalDateTime timestamp;
    private String statusCode;
    
}
