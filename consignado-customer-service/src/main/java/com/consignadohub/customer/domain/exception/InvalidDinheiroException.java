package com.consignadohub.customer.domain.exception;

public class InvalidDinheiroException extends DomainException {
    public InvalidDinheiroException(String message) {
        super(message);
    }

    public InvalidDinheiroException(String message, Throwable cause) {
        super(message, cause);
    }
}
