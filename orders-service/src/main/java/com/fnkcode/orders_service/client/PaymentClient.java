package com.fnkcode.orders_service.client;

import com.fnkcode.orders_service.model.payment.PaymentRequest;
import com.fnkcode.orders_service.model.payment.PaymentStatus;

public interface PaymentClient {

    PaymentStatus process(PaymentRequest request);
}
