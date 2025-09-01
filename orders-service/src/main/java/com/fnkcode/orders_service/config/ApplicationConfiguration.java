package com.fnkcode.orders_service.config;

import com.fnkcode.orders_service.client.*;
import com.fnkcode.orders_service.client.impl.*;
import com.fnkcode.orders_service.orchestrator.OrderOrchestrator;
import com.fnkcode.orders_service.orchestrator.impl.OrderOrchestratorImpl;
import com.fnkcode.orders_service.service.*;
import com.fnkcode.orders_service.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApplicationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);
    private final RestClient.Builder builder;

    public ApplicationConfiguration(RestClient.Builder builder) {
        this.builder = builder.requestInterceptor(new LoggingInterceptor()); // builder might have a list of interceptors. so just add it once.
    }

    @Bean
    public ProductClient productClient(@Value("${product.service.url}") String baseUrl) {
        return new ProductServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public CustomerClient customerClient(@Value("${customer.service.url}") String baseUrl) {
        return new CustomerServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public PaymentClient paymentClient(@Value("${payment.service.url}") String baseUrl) {
        return new PaymentServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public BillingClient billingClient(@Value("${billing.service.url}") String baseUrl) {
        return new BillingServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public ShippingClient shippingClient(@Value("${shipping.service.url}") String baseUrl) {
        return new ShippingServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public RequestValidatorService requestValidatorService(ProductClient productClient, CustomerClient customerClient) {
        return new RequestValidatorServiceImpl(productClient, customerClient);
    }

    @Bean
    public PriceCalculator priceCalculator() {
        return new PriceCalculatorImpl();
    }

    @Bean
    public PaymentBillingService paymentBillingService(PaymentClient paymentClient, BillingClient billingClient) {
        return new PaymentBillingServiceImpl(paymentClient, billingClient);
    }

    @Bean
    public ShippingService shippingService(ShippingClient shippingClient) {
        return new ShippingServiceImpl(shippingClient);
    }

    @Bean
    public OrderOrchestrator orderOrchestrator(RequestValidatorService validatorService,
                                               PriceCalculator priceCalculator,
                                               PaymentBillingService paymentBillingService,
                                               ShippingService shippingService) {
        return new OrderOrchestratorImpl(
                validatorService,
                priceCalculator,
                paymentBillingService,
                shippingService
        );
    }

    @Bean
    public OrderService orderService(OrderOrchestrator orderOrchestrator){
        return new OrderServiceImpl(orderOrchestrator);
    }

    private RestClient buildRestClient(String baseUrl) {
        log.info("base url: {}", baseUrl);
        return this.builder.baseUrl(baseUrl)
                .build();
    }

}
