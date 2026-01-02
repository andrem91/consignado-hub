package com.consignadohub.customer.domain.exception;

public class InvalidTaxaJurosException extends  DomainException {
    public InvalidTaxaJurosException(String message) {
        super(message);
    }

    public InvalidTaxaJurosException(String message, Throwable cause) {
        super(message, cause);
    }

}
