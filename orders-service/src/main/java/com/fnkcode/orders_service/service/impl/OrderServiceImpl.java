package com.fnkcode.orders_service.service.impl;

import com.fnkcode.orders_service.model.order.OrderRequest;
import com.fnkcode.orders_service.model.order.OrderResponse;
import com.fnkcode.orders_service.orchestrator.OrderOrchestrator;
import com.fnkcode.orders_service.orchestrator.OrderState;
import com.fnkcode.orders_service.service.OrderService;
import com.fnkcode.orders_service.util.DomainDtoMapper;

public class OrderServiceImpl implements OrderService {

    private final OrderOrchestrator orderOrchestrator;

    public OrderServiceImpl(OrderOrchestrator orderOrchestrator) {
        this.orderOrchestrator = orderOrchestrator;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        var command = DomainDtoMapper.toCreateOrderCommand(request);
        var placedOrderState =  new OrderState.Placed(command);
        var orderState = this.orderOrchestrator.orchestrate(placedOrderState);
        return switch (orderState){
            case OrderState.Fulfilled fulfilled -> DomainDtoMapper.toOrderResponse(fulfilled.order(), fulfilled.invoice(), fulfilled.shipments());
            default -> throw new IllegalStateException("Unexpected value: " + orderState); // should not happen
        };
    }
}
