package com.fnkcode.orders_service.model.order;

import com.fnkcode.orders_service.model.product.Product;

public record OrderItem(Product product,
                        int quantity) {
}