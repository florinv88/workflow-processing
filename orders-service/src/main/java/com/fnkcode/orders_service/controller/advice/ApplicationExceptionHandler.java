package com.fnkcode.orders_service.controller.advice;

import com.fnkcode.orders_service.exception.ApplicationError;
import com.fnkcode.orders_service.exception.ApplicationException;
import com.fnkcode.orders_service.exception.DomainError.*;
import com.fnkcode.orders_service.exception.SystemError.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleException(ApplicationException ex){
        return switch (ex.getError()){
            case EntityNotFound error -> this.toProblemDetail(error);
            case PaymentDeclined error -> this.toProblemDetail(error);
            case ProductDiscontinued error -> this.toProblemDetail(error);
            case RemoteServiceError error -> this.toProblemDetail(error);
        };
    }

    private ProblemDetail toProblemDetail(EntityNotFound error) {
        return this.build(HttpStatus.BAD_REQUEST, error, problemDetail -> {
            problemDetail.setTitle("Not Found");
            problemDetail.setDetail("Unable to find the requested entity %s for the given id %s".formatted(error.entity(), error.id()));
        });
    }

    private ProblemDetail toProblemDetail(PaymentDeclined error) {
        return this.build(HttpStatus.PAYMENT_REQUIRED, error, problemDetail -> {
            problemDetail.setTitle("Payment Required");
            problemDetail.setDetail("Payment for the order was declined. Please update your payment information and try again");
        });
    }

    private ProblemDetail toProblemDetail(ProductDiscontinued error) {
        return this.build(HttpStatus.BAD_REQUEST, error, problemDetail -> {
            problemDetail.setTitle("Product Discontinued");
            problemDetail.setDetail("The product is discontinued. Check out our top-selling alternatives in the same category");
        });
    }

    private ProblemDetail toProblemDetail(RemoteServiceError error) {
        return this.build(HttpStatus.SERVICE_UNAVAILABLE, error, problemDetail -> {
            problemDetail.setTitle("Service Unavailable");
            problemDetail.setDetail("Unable to fulfill the order. Please try again later");
        });
    }

    private ProblemDetail build(HttpStatus status, ApplicationError error, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatus(status);
        problem.setProperty("additionalInformation", error);
        consumer.accept(problem);
        return problem;
    }

}