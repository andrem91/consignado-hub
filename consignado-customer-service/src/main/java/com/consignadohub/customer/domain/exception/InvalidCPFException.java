package com.consignadohub.customer.domain.exception;

public class InvalidCPFException extends DomainException {
    public InvalidCPFException(String message) {
        super(message);
    }
    public InvalidCPFException(String message, Throwable cause) {
        super(message, cause);
    }
}
