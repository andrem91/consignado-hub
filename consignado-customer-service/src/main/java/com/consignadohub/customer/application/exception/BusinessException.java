package com.consignadohub.customer.application.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static BusinessException clienteJaExiste(String cpf) {
        return new BusinessException("CLIENTE_JA_EXISTE",
                "Cliente com CPF " + cpf + " já cadastrado");
    }
}
