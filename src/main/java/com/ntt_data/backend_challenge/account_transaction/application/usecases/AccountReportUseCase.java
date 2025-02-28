package com.ntt_data.backend_challenge.account_transaction.application.usecases;


import com.ntt_data.backend_challenge.account_transaction.application.dto.AccountDetailsDTO;
import com.ntt_data.backend_challenge.account_transaction.application.dto.AccountReportDTO;
import com.ntt_data.backend_challenge.account_transaction.application.dto.TransactionDetailsDTO;
import com.ntt_data.backend_challenge.account_transaction.domain.AccountEntity;
import com.ntt_data.backend_challenge.account_transaction.domain.TransactionEntity;
import com.ntt_data.backend_challenge.account_transaction.infrastructure.persistence.AccountRepository;
import com.ntt_data.backend_challenge.account_transaction.infrastructure.persistence.TransactionRepository;
import com.ntt_data.backend_challenge.client_person.domain.ClientEntity;
import com.ntt_data.backend_challenge.client_person.infrastructure.persistence.ClientRepository;
import com.ntt_data.backend_challenge.common.exceptions.BadRequestException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountReportUseCase {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public AccountReportDTO generateAccountReport(Long clientId, LocalDate startDate, LocalDate endDate) {
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        List<AccountEntity> accounts = accountRepository.findByClientId(clientId);
        if (accounts.isEmpty()) {
            throw new EntityNotFoundException("No accounts found for this client");
        }

        List<AccountDetailsDTO> accountDetailsList = accounts.stream()
                .map(account -> buildAccountDetails(account, startDate, endDate))
                .collect(Collectors.toList());

        return AccountReportDTO.builder()
                .clientId(client.getId())
                .clientName(client.getPerson().getName())
                .accounts(accountDetailsList)
                .build();
    }

    private AccountDetailsDTO buildAccountDetails(AccountEntity account, LocalDate startDate, LocalDate endDate) {
        List<TransactionEntity> transactions = transactionRepository.findByAccountIdAndTransactionDateBetween(
                        account.getId(), startDate.atStartOfDay(), endDate.atTime(23, 59, 59))
                .stream()
                .sorted(Comparator.comparing(TransactionEntity::getTransactionDate))
                .toList();

        double initialBalance = account.getInitialBalance();
        double runningBalance = initialBalance;

        List<TransactionDetailsDTO> transactionDetails = new ArrayList<>();
        for (TransactionEntity transaction : transactions) {
            runningBalance += transaction.getAmount();
            transactionDetails.add(new TransactionDetailsDTO(
                    transaction.getTransactionDate(),
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    runningBalance
            ));
        }

        boolean insufficientBalance = transactionDetails.stream().anyMatch(tx -> tx.getBalanceAfterTransaction() < 0);

        return AccountDetailsDTO.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .initialBalance(initialBalance)
                .currentBalance(runningBalance)
                .insufficientBalance(insufficientBalance)
                .transactions(transactionDetails)
                .build();
    }


}

