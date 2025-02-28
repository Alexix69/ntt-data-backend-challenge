package com.ntt_data.backend_challenge.account_transaction.application.dto;

import com.ntt_data.backend_challenge.account_transaction.domain.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

    private Long id;

    @NotBlank
    private String accountNumber;

    @NotNull
    private AccountType accountType;

    @NotNull
    private Double initialBalance;

    @NotNull
    private Boolean status;

    @NotNull
    private Long clientId;
}

