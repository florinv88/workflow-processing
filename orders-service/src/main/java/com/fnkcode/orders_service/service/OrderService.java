package com.fnkcode.orders_service.service;

import com.fnkcode.orders_service.model.order.OrderRequest;
import com.fnkcode.orders_service.model.order.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

}
