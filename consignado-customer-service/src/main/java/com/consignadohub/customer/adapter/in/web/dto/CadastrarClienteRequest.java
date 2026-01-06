package com.consignadohub.customer.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastrarClienteRequest(
        @NotBlank String cpf,
        @NotBlank String nome,
        @NotNull LocalDate dataNascimento,
        String email,
        String telefone
) {
}
