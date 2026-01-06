package com.consignadohub.customer.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponse(
        UUID id,
        String cpf,
        String nome,
        LocalDate dataNascimento,
        String email,
        String telefone
) {
}
