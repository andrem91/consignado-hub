package com.consignadohub.customer.application.port.out;

import com.consignadohub.customer.domain.model.Cliente;
import com.consignadohub.customer.domain.vo.CPF;
import com.consignadohub.customer.domain.vo.ClienteId;

import java.util.Optional;

public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(ClienteId id);

    Optional<Cliente> buscarPorCPF(CPF cpf);

    boolean existePorCpf(CPF cpf);
}
