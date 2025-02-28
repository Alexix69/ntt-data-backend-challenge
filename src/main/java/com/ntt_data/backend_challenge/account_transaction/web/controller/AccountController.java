package com.ntt_data.backend_challenge.account_transaction.web.controller;

import com.ntt_data.backend_challenge.account_transaction.application.dto.AccountDTO;
import com.ntt_data.backend_challenge.account_transaction.application.usecases.AccountUseCase;
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
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountUseCase accountUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDTO>>> getAllAccounts() {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Accounts retrieved successfully", accountUseCase.getAllAccounts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Account retrieved successfully", accountUseCase.getAccountById(id)));

    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<AccountDTO>>> getAccountsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Accounts retrieved successfully", accountUseCase.getAccountsByClientId(clientId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDTO>> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.created(URI.create("/v1/accounts")).body(ApiResponse.success(HttpStatus.CREATED, "Account created successfully", accountUseCase.createAccount(accountDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Account updated successfully", accountUseCase.updateAccount(id, accountDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long id) {
        accountUseCase.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(HttpStatus.NO_CONTENT, "Account deleted successfully", null));
    }
}

