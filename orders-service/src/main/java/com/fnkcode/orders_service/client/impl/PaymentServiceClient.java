package com.fnkcode.orders_service.client.impl;

import com.fnkcode.orders_service.client.PaymentClient;
import com.fnkcode.orders_service.model.payment.PaymentRequest;
import com.fnkcode.orders_service.model.payment.PaymentStatus;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class PaymentServiceClient extends AbstractServiceClient implements PaymentClient {

    private final RestClient restClient;

    public PaymentServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "paymentService";
    }

    @Override
    public PaymentStatus process(PaymentRequest request) {
        var errorMap = Map.<Integer, Supplier<PaymentStatus>>of(
                402, () -> new PaymentStatus.Declined(request.orderId(),request.amount())
        );

        return this.executeRequest(
                () -> restClient.post()
                        .uri("/process")
                        .body(request)
                        .retrieve()
                        .body(PaymentStatus.Processed.class),
                errorMap);
    }

}
