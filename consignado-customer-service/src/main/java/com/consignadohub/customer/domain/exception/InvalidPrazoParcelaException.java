package com.consignadohub.customer.domain.exception;

public class InvalidPrazoParcelaException extends DomainException{
    public InvalidPrazoParcelaException(String message) {
        super(message);
    }

    public InvalidPrazoParcelaException(String message, Throwable cause) {
        super(message, cause);
    }
}
