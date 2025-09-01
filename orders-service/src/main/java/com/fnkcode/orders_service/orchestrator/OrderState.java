package com.fnkcode.orders_service.orchestrator;

import com.fnkcode.orders_service.model.common.PriceSummary;
import com.fnkcode.orders_service.model.invoice.Invoice;
import com.fnkcode.orders_service.model.order.CreateOrderCommand;
import com.fnkcode.orders_service.model.order.Order;
import com.fnkcode.orders_service.model.shipping.Shipment;

import java.util.List;

public sealed interface OrderState {

    record Placed(CreateOrderCommand request) implements OrderState {

    }

    record Validated(Order order) implements OrderState {

    }

    record Priced(Order order,
                  PriceSummary priceSummary) implements OrderState {

    }

    record Invoiced(Order order,
                    Invoice invoice) implements OrderState {

    }

    record Shipped(Order order,
                   Invoice invoice,
                   List<Shipment> shipments) implements OrderState {

    }

    record Fulfilled(Order order,
                     Invoice invoice,
                     List<Shipment> shipments) implements OrderState {

    }

}