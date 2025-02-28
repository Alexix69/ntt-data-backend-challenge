package com.ntt_data.backend_challenge.account_transaction.domain;

import com.ntt_data.backend_challenge.account_transaction.domain.enums.AccountType;
import com.ntt_data.backend_challenge.client_person.domain.ClientEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType;

    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;
}

