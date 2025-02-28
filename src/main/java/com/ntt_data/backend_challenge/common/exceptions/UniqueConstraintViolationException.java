package com.ntt_data.backend_challenge.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UniqueConstraintViolationException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UniqueConstraintViolationException(String field, String value) {
        super(String.format("The value '%s' already exists for the field '%s'", value, field));
        this.httpStatus = HttpStatus.CONFLICT;
    }
}
