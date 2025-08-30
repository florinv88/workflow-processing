package com.fnkcode.orders_service.service;

import com.fnkcode.orders_service.model.common.PriceSummary;
import com.fnkcode.orders_service.model.order.Order;

public interface PriceCalculator {

    PriceSummary calculate(Order order);

}
