package com.consignadohub.customer.domain.exception;

public class InvalidPercentualMargemException extends DomainException {
    public InvalidPercentualMargemException(String message) {
        super(message);
    }

    public InvalidPercentualMargemException(String message, Throwable cause) {
        super(message, cause);
    }
}
