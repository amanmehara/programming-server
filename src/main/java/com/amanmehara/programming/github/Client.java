package com.amanmehara.programming.github;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.concurrent.CompletableFuture;

public class Client {

    private final Vertx vertx;
    private final String host;
    private final Integer port;
    private final String token;

    public Client(Vertx vertx, String host, Integer port, String token) {
        this.vertx = vertx;
        this.host = host;
        this.port = port;
        this.token = token;
    }

    private CompletableFuture<HttpResponse<Buffer>> getResponse(String requestURI) {
        CompletableFuture<HttpResponse<Buffer>> completableFuture = new CompletableFuture<>();
        WebClientOptions options = new WebClientOptions().setSsl(true);
        WebClient webClient = WebClient.create(vertx, options);
        webClient.get(port, host, requestURI)
                .putHeader("Authorization", String.format("token %s", token))
                .send(event -> {
                    if (event.succeeded()) {
                        completableFuture.complete(event.result());
                    } else {
                        completableFuture.completeExceptionally(event.cause());
                    }
                });
        return completableFuture;
    }

}