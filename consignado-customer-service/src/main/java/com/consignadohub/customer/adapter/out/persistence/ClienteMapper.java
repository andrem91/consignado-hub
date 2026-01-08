package com.consignadohub.customer.adapter.out.persistence;

import com.consignadohub.customer.domain.model.Cliente;
import com.consignadohub.customer.domain.vo.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClienteMapper {

    public ClienteJpaEntity toEntity(Cliente cliente) {
        ClienteJpaEntity entity = new ClienteJpaEntity();
        entity.setId(cliente.getId().valor());
        entity.setCpf(cliente.getCpf().valor());
        entity.setNome(cliente.getNome());
        entity.setDataNascimento(cliente.getDataNascimento().valor());
        entity.setEmail(cliente.getEmail() != null ? cliente.getEmail().valor() : null);
        entity.setTelefone(cliente.getTelefone() != null ? cliente.getTelefone().valor() : null);
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public Cliente toDomain(ClienteJpaEntity entity) {
        return Cliente.reconstituir(
                ClienteId.of(entity.getId()),
                new CPF(entity.getCpf()),
                entity.getNome(),
                new DataNascimento(entity.getDataNascimento()),
                entity.getEmail() != null ? new Email(entity.getEmail()) : null,
                entity.getTelefone() != null ? new Telefone(entity.getTelefone()) : null
        );
    }
}
