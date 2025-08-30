package com.fnkcode.orders_service.model.order;


import com.fnkcode.orders_service.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record Order(UUID orderId,
                    Customer customer,
                    OrderItem orderItem,
                    LocalDateTime createdAt) {
}