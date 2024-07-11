package com.lifePill.SupplierService.exception;

/**
 * The type Authentication exception.
 */
public class AuthenticationException extends RuntimeException {

    /**
     * Instantiates a new Authentication exception.
     *
     * @param message the message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Authentication exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
