package com.ntt_data.backend_challenge.account_transaction.application.usecases;

import com.ntt_data.backend_challenge.account_transaction.application.dto.AccountDTO;
import com.ntt_data.backend_challenge.account_transaction.application.mapper.AccountMapper;
import com.ntt_data.backend_challenge.account_transaction.domain.AccountEntity;
import com.ntt_data.backend_challenge.account_transaction.infrastructure.persistence.AccountRepository;
import com.ntt_data.backend_challenge.client_person.domain.ClientEntity;
import com.ntt_data.backend_challenge.client_person.infrastructure.persistence.ClientRepository;
import com.ntt_data.backend_challenge.common.exceptions.UniqueConstraintViolationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountUseCase {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientRepository clientRepository;


    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO getAccountById(Long id) {
        AccountEntity account = accountRepository.findById(id).orElseThrow();
        return accountMapper.toDTO(account);
    }

    public List<AccountDTO> getAccountsByClientId(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException("Client with id " + clientId + " doesn't exists");
        }
        return accountRepository.findByClientId(clientId).stream()
                .map(accountMapper::toDTO)
                .toList();
    }

    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        ClientEntity client = clientRepository.findById(accountDTO.getClientId())
                .orElseThrow();

        if (accountRepository.existsByClientIdAndAccountNumber(accountDTO.getClientId(), accountDTO.getAccountNumber())) {
            throw new UniqueConstraintViolationException("accountNumber", accountDTO.getAccountNumber());
        }

        AccountEntity account = accountMapper.toEntity(accountDTO);
        account.setClient(client);
        return accountMapper.toDTO(accountRepository.save(account));
    }

    @Transactional
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        AccountEntity account = accountRepository.findById(id)
                .orElseThrow();

        ClientEntity client = clientRepository.findById(accountDTO.getClientId())
                .orElseThrow();

        boolean exists = accountRepository.findByClientId(client.getId()).stream()
                .anyMatch(acc -> !acc.getId().equals(id) && acc.getAccountNumber().equals(accountDTO.getAccountNumber()));

        if (exists) {
            throw new UniqueConstraintViolationException("accountNumber", accountDTO.getAccountNumber());
        }

        accountMapper.updateAccountFromDto(accountDTO, account);
        account.setClient(client);
        return accountMapper.toDTO(accountRepository.save(account));
    }

    @Transactional
    public void deleteAccount(Long id) {
        AccountEntity account = accountRepository.findById(id).orElseThrow();
        accountRepository.delete(account);
    }
}

