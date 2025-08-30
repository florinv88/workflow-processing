package com.fnkcode.orders_service.client;

import com.fnkcode.orders_service.model.product.ProductStatus;

public interface ProductClient {

    ProductStatus getProductStatus(String productId);
}
