package com.fnkcode.orders_service.service.impl;

import com.fnkcode.orders_service.client.BillingClient;
import com.fnkcode.orders_service.client.PaymentClient;
import com.fnkcode.orders_service.exception.ApplicationExceptions;
import com.fnkcode.orders_service.model.common.PriceSummary;
import com.fnkcode.orders_service.model.customer.Customer.*;
import com.fnkcode.orders_service.model.invoice.Invoice;
import com.fnkcode.orders_service.model.invoice.InvoiceRequest;
import com.fnkcode.orders_service.model.order.Order;
import com.fnkcode.orders_service.model.payment.PaymentRequest;
import com.fnkcode.orders_service.model.payment.PaymentStatus.*;
import com.fnkcode.orders_service.service.PaymentBillingService;

public class PaymentBillingServiceImpl implements PaymentBillingService {

    private final PaymentClient paymentClient;
    private final BillingClient billingClient;

    public PaymentBillingServiceImpl(PaymentClient paymentClient, BillingClient billingClient) {
        this.paymentClient = paymentClient;
        this.billingClient = billingClient;
    }

    @Override
    public Invoice processPayment(Order order, PriceSummary priceSummary) {
        var paymentRequest = new PaymentRequest(order.customer().id(), order.orderId(), priceSummary.finalAmount());
        var paymentStatus = this.paymentClient.process(paymentRequest);
        return switch (paymentStatus){
            case Processed processed -> this.toPaidInvoice(order, priceSummary, processed);
            case Declined declined -> this.toUnpaidInvoice(order, priceSummary, declined);
        };
    }

    private Invoice toPaidInvoice(Order order, PriceSummary priceSummary, Processed processed){
        var request = new InvoiceRequest.Paid(order.orderId(), order.customer().id(), processed.transactionId(), priceSummary);
        return this.billingClient.createInvoice(request);
    }

    private Invoice toUnpaidInvoice(Order order, PriceSummary priceSummary, Declined declined){
        return switch (order.customer()){
            case Regular _ -> ApplicationExceptions.decliendPayment(declined);
            case Business business -> this.toUnpaidInvoice(order, priceSummary, business);
        };
    }

    private Invoice toUnpaidInvoice(Order order, PriceSummary priceSummary, Business business){
        var request = new InvoiceRequest.Unpaid(
                order.orderId(),
                business.id(),
                business.name(),
                business.taxId(),
                priceSummary
        );
        return this.billingClient.createInvoice(request);
    }

}