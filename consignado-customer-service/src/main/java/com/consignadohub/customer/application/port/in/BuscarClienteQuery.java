package com.consignadohub.customer.application.port.in;

import com.consignadohub.customer.domain.model.Cliente;
import com.consignadohub.customer.domain.vo.CPF;
import com.consignadohub.customer.domain.vo.ClienteId;

import java.util.Optional;

public interface BuscarClienteQuery {
    Optional<Cliente> buscarPorCpf(CPF cpf);
    Optional<Cliente> buscarPorId(ClienteId id);
}
