package com.consignadohub.simulation.application.exception;

/**
 * Exceção para erros de regras de negócio na camada Application.
 * Usada quando uma operação viola regras de negócio (ex: CPF já cadastrado).
 * Diferente de DomainException, que é para validação de dados no domínio.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

}
