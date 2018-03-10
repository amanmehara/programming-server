/*
 * Copyright 2018 Aman Mehara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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