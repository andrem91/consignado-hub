package com.consignadohub.customer.application.exception;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException(String cpf) {
        super("Cliente não encontrado: " + cpf);
    }

}
