package com.consignadohub.customer.adapter.out.persistence;

import com.consignadohub.customer.application.port.out.ClienteRepository;
import com.consignadohub.customer.domain.model.Cliente;
import com.consignadohub.customer.domain.vo.CPF;
import com.consignadohub.customer.domain.vo.ClienteId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final ClienteJpaRepository jpaRepository;
    private final ClienteMapper mapper;


    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteJpaEntity entity = mapper.toEntity(cliente);
        jpaRepository.save(entity);
        return cliente;
    }

    @Override
    public Optional<Cliente> buscarPorId(ClienteId id) {
        return jpaRepository.findById(id.valor()).map(mapper::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorCPF(CPF cpf) {
        return jpaRepository.findByCpf(cpf.valor()).map(mapper::toDomain);
    }

    @Override
    public boolean existePorCpf(CPF cpf) {
        return jpaRepository.existsByCpf(cpf.valor());
    }
}
