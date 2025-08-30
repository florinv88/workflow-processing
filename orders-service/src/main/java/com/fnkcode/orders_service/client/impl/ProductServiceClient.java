package com.fnkcode.orders_service.client.impl;

import com.fnkcode.orders_service.client.ProductClient;
import com.fnkcode.orders_service.exception.ApplicationExceptions;
import com.fnkcode.orders_service.model.product.ProductStatus;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class ProductServiceClient extends AbstractServiceClient implements ProductClient {

    private final RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "productService";
    }

    @Override
    public ProductStatus getProductStatus(String productId) {
        //http://localhost:7070/products/{productId}
        var errorMap = Map.<Integer, Supplier<ProductStatus>>of(
                404, () -> ApplicationExceptions.productNotFound(productId)
        );
        return this.executeRequest(
                () -> restClient.get()
                        .uri("/{productId}", productId)
                        .retrieve()
                        .body(ProductStatus.class),
                errorMap);
    }
}
