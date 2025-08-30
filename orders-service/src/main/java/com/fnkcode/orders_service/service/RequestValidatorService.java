package com.fnkcode.orders_service.service;

import com.fnkcode.orders_service.model.order.CreateOrderCommand;
import com.fnkcode.orders_service.model.order.Order;

public interface RequestValidatorService {

    Order validate(CreateOrderCommand request);
}
