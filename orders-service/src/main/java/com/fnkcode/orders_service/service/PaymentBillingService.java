package com.fnkcode.orders_service.service;

import com.fnkcode.orders_service.model.common.PriceSummary;
import com.fnkcode.orders_service.model.invoice.Invoice;
import com.fnkcode.orders_service.model.order.Order;

public interface PaymentBillingService {

    Invoice processPayment(Order order, PriceSummary priceSummary);

}