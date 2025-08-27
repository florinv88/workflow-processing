package com.fnkcode.orders_service.exception;

public sealed interface SystemError extends ApplicationError {

    record RemoteServiceError(String service,
                              String message) implements SystemError {

    }
}