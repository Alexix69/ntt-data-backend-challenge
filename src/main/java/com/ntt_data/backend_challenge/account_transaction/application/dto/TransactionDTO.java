package com.ntt_data.backend_challenge.account_transaction.application.dto;

import com.ntt_data.backend_challenge.account_transaction.domain.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long id;
    private LocalDateTime transactionDate;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private Double amount;

    private Double balance;

    @NotNull
    private Long accountId;
}


