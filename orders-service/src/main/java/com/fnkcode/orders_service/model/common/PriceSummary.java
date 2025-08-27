package com.fnkcode.orders_service.model.common;

// finalAmount = subTotal - discountApplied + taxAmount
public record PriceSummary(double subTotal,
                           double discountApplied,
                           double taxAmount,
                           double finalAmount) {
}