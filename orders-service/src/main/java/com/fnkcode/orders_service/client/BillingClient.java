package com.fnkcode.orders_service.client;

import com.fnkcode.orders_service.model.invoice.Invoice;
import com.fnkcode.orders_service.model.invoice.InvoiceRequest;

public interface BillingClient {

    Invoice createInvoice(InvoiceRequest request);
}
