package com.fnkcode.orders_service.exception;

public sealed interface ApplicationError permits DomainError, SystemError {
}
