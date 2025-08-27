package com.fnkcode.orders_service.model.shipping;

public record Shipment(String shipmentId,
                       String productId,
                       int quantity,
                       String shippingAddress,
                       TrackingDetails trackingDetails) {
}