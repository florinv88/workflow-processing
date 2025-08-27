package com.fnkcode.orders_service.model.invoice;

import com.fnkcode.orders_service.model.common.PriceSummary;

import java.time.LocalDate;
import java.util.UUID;

public sealed interface Invoice {

    record Paid(String id,
                UUID orderId,
                String customerId,
                String transactionId,
                PriceSummary priceSummary) implements Invoice {

    }

    record Unpaid(String id,
                  UUID orderId,
                  String customerId,
                  String businessTaxId,
                  PriceSummary priceSummary,
                  LocalDate paymentDue) implements Invoice {

    }

}