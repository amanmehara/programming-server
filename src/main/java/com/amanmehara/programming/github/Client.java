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
import java.util.concurrent.Executor;

public class Client {

    private final Vertx vertx;
    private final Executor executor;
    private final String host;
    private final int port;
    private final String token;

    public Client(Vertx vertx, Executor executor, String host, int port, String token) {
        this.vertx = vertx;
        this.executor = executor;
        this.host = host;
        this.port = port;
        this.token = token;
    }

    public CompletableFuture<HttpResponse<Buffer>> getResponse(
            String requestURI) {
        var completableFuture = new CompletableFuture<HttpResponse<Buffer>>();
        var options = new WebClientOptions().setSsl(true);
        var webClient = WebClient.create(vertx, options);
        webClient.get(port, host, requestURI)
                .putHeader("Authorization", String.format("token %s", token))
                .send(event -> {
                    if (event.succeeded()) {
                        completableFuture.completeAsync(event::result, executor);
                    } else {
                        completableFuture.completeExceptionally(event.cause());
                    }
                });
        return completableFuture;
    }

}
