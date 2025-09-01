package com.fnkcode.orders_service.service.impl;

import com.fnkcode.orders_service.client.ShippingClient;
import com.fnkcode.orders_service.model.order.Order;
import com.fnkcode.orders_service.model.product.Product.*;
import com.fnkcode.orders_service.model.shipping.Recipient;
import com.fnkcode.orders_service.model.shipping.ShipmentItem;
import com.fnkcode.orders_service.model.shipping.ShippingRequest;
import com.fnkcode.orders_service.model.shipping.ShippingResponse;
import com.fnkcode.orders_service.service.ShippingService;

import java.util.List;

public class ShippingServiceImpl implements ShippingService {

    private final ShippingClient shippingClient;

    public ShippingServiceImpl(ShippingClient shippingClient) {
        this.shippingClient = shippingClient;
    }

    @Override
    public ShippingResponse scheduleShipping(Order order) {
        var request = this.toShippingRequest(order);
        return this.shippingClient.schedule(request);
    }

    private ShippingRequest toShippingRequest(Order order) {
        var recipient = new Recipient(order.customer().name(), order.customer().address());
        var quantity = order.orderItem().quantity();
        var items = switch (order.orderItem().product()) {
            case Single single -> List.of(new ShipmentItem(single.productId(), quantity));
            case Bundle bundle -> bundle.items()
                    .stream()
                    .map(Single::productId)
                    .map(id -> new ShipmentItem(id, quantity))
                    .toList();
        };
        return new ShippingRequest(order.orderId(), recipient, items);
    }
}
