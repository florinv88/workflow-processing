package com.fnkcode.orders_service.client;

import com.fnkcode.orders_service.model.customer.Customer;

public interface CustomerClient {

    Customer getCustomer(String customerId);
}
