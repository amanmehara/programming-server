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

import com.amanmehara.programming.graph.Node;
import com.amanmehara.programming.model.GitHubCache;
import com.amanmehara.programming.model.GitHubContent;
import com.amanmehara.programming.graph.DirectedAcyclicGraph;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.Json;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class CacheService {

    private final Client client;
    private final String contentURI;

    public CacheService(Client client, String contentURI) {
        this.client = client;
        this.contentURI = contentURI;
    }

    private CompletableFuture<GitHubContent> getSuccessor(
            String requestURI) {
        return client.getResponse(requestURI).thenApply(response ->
                Json.decodeValue(response.body(), new TypeReference<>() {
                }));
    }

    private CompletableFuture<Set<GitHubContent>> getSuccessors(
            String requestURI) {
        return client.getResponse(requestURI).thenApply(response ->
                Json.decodeValue(response.body(), new TypeReference<>() {
                }));
    }

    public GitHubCache buildCache() {

        var roots = getSuccessors(contentURI).thenApply(gitHubContents ->
                gitHubContents.stream().map(Node::new).collect(Collectors.toSet())).join();
        var dag = new DirectedAcyclicGraph<>(roots);

        UnaryOperator<Node<GitHubContent>> operator = node -> {
            var type = node.data().type();
            // TODO: Use requestURI instead of absoluteURI
            var url = node.data().url();
            switch (type) {
                case "file":
                    var successor = getSuccessor(url).thenApply(Node::new).join();
                    if (!node.equals(successor)) {
                        node.successor(successor);
                    }
                    break;
                case "dir":
                    var successors = getSuccessors(url).thenApply(gitHubContents ->
                            gitHubContents.stream().map(Node::new).collect(Collectors.toSet())).join();
                    node.successors(successors);
                    break;
                default:

            }
            return node;
        };

        dag.traverse(operator);

        return new GitHubCache.GitHubCacheBuilder().dag(dag).build();

    }

}
