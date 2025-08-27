package com.fnkcode.orders_service.model.shipping;

import com.fnkcode.orders_service.model.common.Address;

public record Recipient(String name,
                        Address address) {
}