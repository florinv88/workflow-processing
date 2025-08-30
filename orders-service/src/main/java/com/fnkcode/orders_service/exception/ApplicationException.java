package com.fnkcode.orders_service.exception;

public class ApplicationException extends RuntimeException {

    private final ApplicationError error;

    public ApplicationException(ApplicationError error) {
        this.error = error;
    }

    public ApplicationError getError() {
        return error;
    }
}
