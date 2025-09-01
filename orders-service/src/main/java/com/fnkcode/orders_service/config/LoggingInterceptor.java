package com.fnkcode.orders_service.config;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StreamUtils.copyToString;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    public static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    public ClientHttpResponse intercept(HttpRequest request, byte[] body,ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        var bodyAsString = new String(body, UTF_8);
        log.info("{} [{}]", request.getURI(), request.getMethod());
        log.info("request : {}", bodyAsString);
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        var bodyAsString = copyToString(response.getBody(), defaultCharset());
        log.info("{} [{}]", response.getStatusText(), response.getStatusCode());
    }
}
