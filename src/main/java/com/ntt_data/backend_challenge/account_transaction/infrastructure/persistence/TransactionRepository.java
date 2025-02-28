package com.ntt_data.backend_challenge.account_transaction.infrastructure.persistence;

import com.ntt_data.backend_challenge.account_transaction.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByAccountIdAndTransactionDateBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
    Optional<TransactionEntity> findByAccountId(Long accountId);
}
