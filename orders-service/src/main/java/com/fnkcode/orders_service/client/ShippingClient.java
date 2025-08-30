package com.fnkcode.orders_service.client;

import com.fnkcode.orders_service.model.shipping.ShippingRequest;
import com.fnkcode.orders_service.model.shipping.ShippingResponse;

public interface ShippingClient {

    ShippingResponse schedule(ShippingRequest request);
}
