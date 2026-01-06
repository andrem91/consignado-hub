package com.consignadohub.customer.application.port.in.command;

import com.consignadohub.customer.domain.vo.ClienteId;

public interface CadastrarClienteUseCase {
    ClienteId executar(CadastrarClienteCommand command);
}
