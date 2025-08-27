package com.fnkcode.orders_service.model.invoice;

import com.fnkcode.orders_service.model.common.PriceSummary;

import java.util.UUID;

public sealed interface InvoiceRequest {

    record Paid(UUID orderId,
                String customerId,
                String transactionId,
                PriceSummary priceSummary) implements InvoiceRequest {

    }

    record Unpaid(UUID orderId,
                  String customerId,
                  String businessName,
                  String businessTaxId,
                  PriceSummary priceSummary) implements InvoiceRequest {

    }

}