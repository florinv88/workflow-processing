package com.fnkcode.orders_service.client.impl;

import com.fnkcode.orders_service.client.ShippingClient;
import com.fnkcode.orders_service.model.shipping.ShippingRequest;
import com.fnkcode.orders_service.model.shipping.ShippingResponse;
import org.springframework.web.client.RestClient;

import java.util.Collections;

public class ShippingServiceClient extends AbstractServiceClient implements ShippingClient {

    private final RestClient restClient;

    public ShippingServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "shippingService";
    }

    @Override
    public ShippingResponse schedule(ShippingRequest request) {
        return this.executeRequest(
                () -> restClient.post()
                        .uri("/schedule")
                        .retrieve()
                        .body(ShippingResponse.class),
                Collections.emptyMap());
    }
}
