package com.fnkcode.orders_service.client.impl;

import com.fnkcode.orders_service.client.BillingClient;
import com.fnkcode.orders_service.model.invoice.Invoice;
import com.fnkcode.orders_service.model.invoice.InvoiceRequest;
import com.fnkcode.orders_service.model.invoice.InvoiceRequest.*;
import org.springframework.web.client.RestClient;

import java.util.Collections;

public class BillingServiceClient extends AbstractServiceClient implements BillingClient {

    private final RestClient restClient;

    public BillingServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "billingService";
    }

    @Override
    public Invoice createInvoice(InvoiceRequest request) {
        return switch (request) {
            case Paid ignored -> this.executeRequest("/invoices/paid",request);
            case Unpaid ignored -> this.executeRequest("/invoices/unpaid",request);
        };
    }
    private Invoice executeRequest(String path, InvoiceRequest request){
        return this.executeRequest(
                ()-> this.restClient.post()
                        .uri(path)
                        .body(request)
                        .retrieve()
                        .body(Invoice.class),
                Collections.emptyMap()
        );
    }

}
