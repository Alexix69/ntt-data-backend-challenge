package com.ntt_data.backend_challenge.account_transaction.web.controller;


import com.ntt_data.backend_challenge.account_transaction.application.dto.TransactionDTO;
import com.ntt_data.backend_challenge.account_transaction.application.usecases.TransactionUseCase;
import com.ntt_data.backend_challenge.common.dtos.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getAllTransactions() {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Transactions retrieved successfully", transactionUseCase.getAllTransactions()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionDTO>> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Transaction retrieved successfully", transactionUseCase.getTransactionById(id)));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionsByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Transactions retrieved successfully", transactionUseCase.getTransactionsByAccountId(accountId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionDTO>> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.created(URI.create("/v1/transactions"))
                .body(ApiResponse.success(HttpStatus.CREATED, "Transaction registered successfully", transactionUseCase.createTransaction(transactionDTO)));
    }
}

