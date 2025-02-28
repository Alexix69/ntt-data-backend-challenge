package com.ntt_data.backend_challenge.account_transaction.application.usecases;

import com.ntt_data.backend_challenge.account_transaction.application.dto.TransactionDTO;
import com.ntt_data.backend_challenge.account_transaction.application.mapper.TransactionMapper;
import com.ntt_data.backend_challenge.account_transaction.domain.AccountEntity;
import com.ntt_data.backend_challenge.account_transaction.domain.TransactionEntity;
import com.ntt_data.backend_challenge.account_transaction.domain.enums.TransactionType;
import com.ntt_data.backend_challenge.account_transaction.infrastructure.persistence.AccountRepository;
import com.ntt_data.backend_challenge.account_transaction.infrastructure.persistence.TransactionRepository;
import com.ntt_data.backend_challenge.common.exceptions.BadRequestException;
import com.ntt_data.backend_challenge.common.exceptions.InsufficientBalanceException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toDTO)
                .toList();
    }

    public TransactionDTO getTransactionById(Long id) {
        TransactionEntity transaction = transactionRepository.findById(id)
                .orElseThrow();
        return transactionMapper.toDTO(transaction);
    }

    public List<TransactionDTO> getTransactionsByAccountId(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException("Account with id " + accountId + " doesn't exist");
        }
        return transactionRepository.findByAccountId(accountId).stream()
                .map(transactionMapper::toDTO)
                .toList();
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        AccountEntity account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if ((transactionDTO.getTransactionType() == TransactionType.DEPOSIT && transactionDTO.getAmount() < 0) ||
                (transactionDTO.getTransactionType() == TransactionType.WITHDRAWAL && transactionDTO.getAmount() > 0)) {
            throw new BadRequestException("Invalid transaction amount for type: " + transactionDTO.getTransactionType());
        }

        double newBalance = account.getInitialBalance() + transactionDTO.getAmount();

        if (transactionDTO.getTransactionType() == TransactionType.WITHDRAWAL && newBalance < 0) {
            throw new InsufficientBalanceException();
        }

        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setBalance(newBalance);
        transaction.setAccount(account);
        transactionRepository.save(transaction);

        account.setInitialBalance(newBalance);
        accountRepository.save(account);

        return transactionMapper.toDTO(transaction);
    }

}


