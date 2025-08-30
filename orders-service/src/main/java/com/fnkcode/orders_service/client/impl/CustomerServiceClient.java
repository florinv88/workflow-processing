package com.fnkcode.orders_service.client.impl;

import com.fnkcode.orders_service.client.CustomerClient;
import com.fnkcode.orders_service.exception.ApplicationExceptions;
import com.fnkcode.orders_service.model.customer.Customer;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class CustomerServiceClient extends AbstractServiceClient implements CustomerClient {

    private final RestClient restClient;

    public CustomerServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "customerService";
    }

    @Override
    public Customer getCustomer(String customerId) {
        var errorMap = Map.<Integer, Supplier<Customer>>of(
                404, () -> ApplicationExceptions.customerNotFound(customerId)
        );
        return this.executeRequest(
                () -> restClient.get()
                        .uri("/{customerId}", customerId)
                        .retrieve()
                        .body(Customer.class),
                errorMap);
    }

}
