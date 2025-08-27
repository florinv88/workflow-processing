package com.fnkcode.orders_service.model.payment;

import java.util.UUID;

public record PaymentRequest(String customerId,
                             UUID orderId,
                             double amount) {
}