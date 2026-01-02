package com.consignadohub.customer.domain.exception;

public class InvalidNumeroBeneficioException extends DomainException {
    public InvalidNumeroBeneficioException(String message) {
        super(message);
    }

    public InvalidNumeroBeneficioException(String message, Throwable cause) {
        super(message, cause);
    }
}
