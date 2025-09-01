package com.fnkcode.orders_service.util;


import com.fnkcode.orders_service.model.invoice.Invoice;
import com.fnkcode.orders_service.model.order.CreateOrderCommand;
import com.fnkcode.orders_service.model.order.Order;
import com.fnkcode.orders_service.model.order.OrderRequest;
import com.fnkcode.orders_service.model.order.OrderResponse;
import com.fnkcode.orders_service.model.product.Product.*;
import com.fnkcode.orders_service.model.shipping.Shipment;

import java.util.List;
import java.util.Optional;

public class DomainDtoMapper {

    private static final String COMPLETED = "Completed";
    private static final String PAID = "Paid";
    private static final String UNPAID = "Unpaid";

    public static CreateOrderCommand toCreateOrderCommand(OrderRequest request) {
        return CreateOrderCommand.create(
                request.customerId(),
                request.productId(),
                request.quantity()
        );
    }

    public static OrderResponse toOrderResponse(Order order, Invoice invoice, List<Shipment> shipments) {
        return new OrderResponse(
                order.orderId(),
                COMPLETED,
                toProducts(order),
                toInvoiceDetails(invoice),
                shipments
        );
    }

    private static OrderResponse.InvoiceDetails toInvoiceDetails(Invoice invoice){
        return switch (invoice){
            case Invoice.Paid paid -> new OrderResponse.InvoiceDetails(paid.id(), PAID, paid.priceSummary(), Optional.empty());
            case Invoice.Unpaid unpaid -> new OrderResponse.InvoiceDetails(unpaid.id(), UNPAID, unpaid.priceSummary(), Optional.of(unpaid.paymentDue()));
        };
    }

    private static List<OrderResponse.Product> toProducts(Order order) {
        var quantity = order.orderItem().quantity();
        return switch (order.orderItem().product()) {
            case Single single -> List.of(toProduct(single, quantity));
            case Bundle bundle -> bundle.items()
                    .stream()
                    .map(single -> toProduct(single, quantity))
                    .toList();
        };
    }

    private static OrderResponse.Product toProduct(Single single, int quantity){
        return new OrderResponse.Product(
                single.productId(),
                single.name(),
                single.price(),
                quantity
        );
    }


}
