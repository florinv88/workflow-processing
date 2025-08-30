package com.fnkcode.orders_service.service.impl;

import com.fnkcode.orders_service.client.CustomerClient;
import com.fnkcode.orders_service.client.ProductClient;
import com.fnkcode.orders_service.exception.ApplicationExceptions;
import com.fnkcode.orders_service.model.order.CreateOrderCommand;
import com.fnkcode.orders_service.model.order.Order;
import com.fnkcode.orders_service.model.order.OrderItem;
import com.fnkcode.orders_service.model.product.Product;
import com.fnkcode.orders_service.model.product.ProductStatus;
import com.fnkcode.orders_service.service.RequestValidatorService;

import java.time.LocalDateTime;

public class RequestValidatorServiceImpl implements RequestValidatorService {

    private final ProductClient productClient;
    private final CustomerClient customerClient;

    public RequestValidatorServiceImpl(ProductClient productClient, CustomerClient customerClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    @Override
    public Order validate(CreateOrderCommand request) {
        var product = this.getProduct(request.productId());
        var customer = customerClient.getCustomer(request.customerId());
        var orderItem = new OrderItem(product, request.quantity());
        return new Order(request.orderId(),customer,orderItem,LocalDateTime.now());
    }

    private Product getProduct(String productId) {
        return switch (this.productClient.getProductStatus(productId)){
            case ProductStatus.Active active -> active.product();
            case ProductStatus.Discontinued discontinued -> ApplicationExceptions.discontinuedProduct(discontinued);
        };
    }
}
