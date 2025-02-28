package com.ntt_data.backend_challenge.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InsufficientBalanceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public InsufficientBalanceException() {
        super("Insufficient Balance");
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}

