package com.ntt_data.backend_challenge.client_person.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private PersonEntity person;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;
}

