package com.consignadohub.customer.application.port.in;

import java.time.LocalDate;

public record CadastrarClienteCommand(
        String cpf,
        String nome,
        LocalDate dataNascimento,
        String email,      // opcional
        String telefone    // opcional
) {}
