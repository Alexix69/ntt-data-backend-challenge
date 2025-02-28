package com.ntt_data.backend_challenge.account_transaction.infrastructure.persistence;

import com.ntt_data.backend_challenge.account_transaction.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByClientId(Long clientId);

    boolean existsByClientIdAndAccountNumber(Long clientId, String accountNumber);
}
