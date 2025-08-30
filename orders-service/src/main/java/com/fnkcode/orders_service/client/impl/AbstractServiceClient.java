package com.fnkcode.orders_service.client.impl;

import com.fnkcode.orders_service.exception.ApplicationExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;


abstract class AbstractServiceClient {

    public static final Logger log = LoggerFactory.getLogger(AbstractServiceClient.class);

    protected abstract String getServiceName();

    protected <T> T executeRequest(Supplier<T> supplier, Map<Integer,Supplier<T>> errorMap) {
        try {
            var t = supplier.get();
            log.info("response: {}",t);
            return t;
        } catch (HttpStatusCodeException e) {
            log.error("error response from {}", this.getServiceName(), e);
            return Optional.ofNullable(errorMap.get(e.getStatusCode().value()))
                    .map(Supplier::get)
                    .orElseGet(()-> ApplicationExceptions.remoteServiceError(this.getServiceName(),e.getMessage()));
        }
    }
}
