package com.consignadohub.customer.domain.exception;

public class InvalidEmailException extends DomainException{
    public InvalidEmailException(String message) {
        super(message);
    }
}
