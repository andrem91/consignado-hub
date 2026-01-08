package com.consignadohub.customer.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, UUID> {
    Optional<ClienteJpaEntity> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
