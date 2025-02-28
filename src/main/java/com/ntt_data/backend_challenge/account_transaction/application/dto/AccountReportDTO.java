package com.ntt_data.backend_challenge.account_transaction.application.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountReportDTO {
    private Long clientId;
    private String clientName;
    private List<AccountDetailsDTO> accounts;
}

