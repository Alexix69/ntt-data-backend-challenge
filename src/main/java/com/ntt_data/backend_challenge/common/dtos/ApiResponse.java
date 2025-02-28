package com.ntt_data.backend_challenge.common.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private boolean success;
    private String message;
    private T data;
    private Map<String, String> errors;

    public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, Map<String, String> errors) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .success(false)
                .message(message)
                .errors(errors)
                .build();
    }
}

