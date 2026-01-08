package com.consignadohub.customer.adapter.in.web;

import com.consignadohub.customer.adapter.in.web.dto.CadastrarClienteRequest;
import com.consignadohub.customer.adapter.in.web.dto.ClienteResponse;
import com.consignadohub.customer.application.exception.NotFoundException;
import com.consignadohub.customer.application.port.in.CadastrarClienteCommand;
import com.consignadohub.customer.application.port.in.CadastrarClienteUseCase;
import com.consignadohub.customer.application.port.in.BuscarClienteQuery;
import com.consignadohub.customer.domain.model.Cliente;
import com.consignadohub.customer.domain.vo.CPF;
import com.consignadohub.customer.domain.vo.ClienteId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final CadastrarClienteUseCase cadastrarCliente;
    private final BuscarClienteQuery buscarCliente;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse cadastrar(@Valid @RequestBody CadastrarClienteRequest request) {
        CadastrarClienteCommand command = new CadastrarClienteCommand(
                request.cpf(),
                request.nome(),
                request.dataNascimento(),
                request.email(),
                request.telefone()
        );

        ClienteId id = cadastrarCliente.executar(command);

        Cliente cliente = buscarCliente.buscarPorId(id)
                .orElseThrow(() -> NotFoundException.cliente("Cliente não encontrado"));

        return toResponse(cliente);
    }

    @GetMapping("/{cpf}")
    public ClienteResponse buscarPorCpf(@PathVariable String cpf) {
        return buscarCliente.buscarPorCpf(new CPF(cpf))
                .map(this::toResponse)
                .orElseThrow(() -> NotFoundException.cliente(cpf));
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId().valor(),
                cliente.getCpf().formatar(),
                cliente.getNome(),
                cliente.getDataNascimento().valor(),
                cliente.getEmail() != null ? cliente.getEmail().valor() : null,
                cliente.getTelefone() != null ? cliente.getTelefone().valor() : null
        );
    }
}
