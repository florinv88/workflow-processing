package com.fnkcode.orders_service.config.mixin;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fnkcode.orders_service.model.product.Product;
import org.springframework.boot.jackson.JsonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Product.Single.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Product.Single.class),
        @JsonSubTypes.Type(Product.Bundle.class),
})
@JsonMixin(Product.class)
public class ProductMixIn {
}
