package com.consignadohub.customer.application.service;

import com.consignadohub.customer.application.exception.ClienteJaExisteException;
import com.consignadohub.customer.application.port.in.command.CadastrarClienteCommand;
import com.consignadohub.customer.application.port.out.ClienteRepository;
import com.consignadohub.customer.domain.model.Cliente;
import com.consignadohub.customer.domain.vo.CPF;
import com.consignadohub.customer.domain.vo.ClienteId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ClienteService")
public class ClienteServiceTest {

    private ClienteService service;
    private ClienteRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(ClienteRepository.class);
        service = new ClienteService(repository);
    }

    @Test
    @DisplayName("Deve cadastrar cliente com dados válidos")
    void deveCadastrarClienteComDadosValidos() {
        CadastrarClienteCommand command = new CadastrarClienteCommand(
                "52998224725",
                "Maria Silva",
                LocalDate.of(1960, 5, 15),
                "maria@email.com",
                "11999999999"
        );

        ClienteId id = service.executar(command);

        assertThat(id).isNotNull();
        verify(repository).salvar(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve rejeitar CPF já cadastrado")
    void deveRejeitarCpfJaCadastrado() {
        CPF cpf = new CPF("52998224725");
        when(repository.existePorCpf(cpf)).thenReturn(true);

        CadastrarClienteCommand command = new CadastrarClienteCommand(
                "52998224725",
                "Maria Silva",
                LocalDate.of(1960, 5, 15), null, null
                );

        assertThatThrownBy(() -> service.executar(command))
                .isInstanceOf(ClienteJaExisteException.class);
    }

}

