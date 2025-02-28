package com.ntt_data.backend_challenge.account_transaction.application.dto;

import com.ntt_data.backend_challenge.account_transaction.domain.enums.AccountType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetailsDTO {
    private Long accountId;
    private String accountNumber;
    private AccountType accountType;
    private Double initialBalance;
    private Double currentBalance;
    private boolean insufficientBalance;
    private List<TransactionDetailsDTO> transactions;
}
