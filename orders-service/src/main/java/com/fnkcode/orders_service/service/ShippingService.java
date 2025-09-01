package com.fnkcode.orders_service.service;

import com.fnkcode.orders_service.model.order.Order;
import com.fnkcode.orders_service.model.shipping.ShippingResponse;

public interface ShippingService {

    ShippingResponse scheduleShipping(Order order);

}
