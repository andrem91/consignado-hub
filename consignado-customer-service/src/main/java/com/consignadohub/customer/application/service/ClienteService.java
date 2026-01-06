package com.consignadohub.customer.application.service;

import com.consignadohub.customer.application.exception.ClienteJaExisteException;
import com.consignadohub.customer.application.port.in.command.CadastrarClienteCommand;
import com.consignadohub.customer.application.port.in.command.CadastrarClienteUseCase;
import com.consignadohub.customer.application.port.in.query.BuscarClienteQuery;
import com.consignadohub.customer.application.port.out.ClienteRepository;
import com.consignadohub.customer.domain.model.Cliente;
import com.consignadohub.customer.domain.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService implements CadastrarClienteUseCase, BuscarClienteQuery {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteId executar(CadastrarClienteCommand command) {
        CPF cpf = new CPF(command.cpf());

        if (clienteRepository.existePorCpf(cpf)) {
            throw new ClienteJaExisteException("CPF já cadastrado");
        }

        Cliente cliente = new Cliente(
                cpf,
                command.nome(),
                new DataNascimento(command.dataNascimento()),
                new Email(command.email()),
                new Telefone(command.telefone())
        );

        clienteRepository.salvar(cliente);

        return cliente.getId();
    }

    @Override
    public Optional<Cliente> buscarPorCpf(CPF cpf) {
        return clienteRepository.buscarPorCPF(cpf);
    }

    @Override
    public Optional<Cliente> buscarPorId(ClienteId id) {
        return clienteRepository.buscarPorId(id);
    }

}
