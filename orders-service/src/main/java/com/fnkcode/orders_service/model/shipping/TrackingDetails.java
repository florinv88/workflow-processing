package com.fnkcode.orders_service.model.shipping;

import java.time.LocalDate;

public record TrackingDetails(String carrier,
                              String trackingNumber,
                              LocalDate estimatedDeliveryDate) {
}
