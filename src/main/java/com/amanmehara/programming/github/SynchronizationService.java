/*
 * Copyright 2019 Aman Mehara
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

import com.amanmehara.programming.graph.DirectedAcyclicGraph;
import com.amanmehara.programming.graph.Node;
import com.amanmehara.programming.model.GitHubCache;
import com.amanmehara.programming.model.GitHubContent;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.Json;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SynchronizationService {

    private final Client client;
    private final Executor executor;

    public SynchronizationService(Client client, Executor executor) {
        this.client = client;
        this.executor = executor;
    }

    public GitHubCache synchronize(GitHubCache cache) {
        var roots = cache.dag().roots().stream()
                .map(root -> synchronize(root, cache.nodes()))
                .collect(Collectors.toSet());
        var dag = new DirectedAcyclicGraph<>(roots);
        return new GitHubCache.GitHubCacheBuilder().dag(dag).build();
    }

    private Node<GitHubContent> synchronize(Node<GitHubContent> node,
                                            Map<String, Node<GitHubContent>> shaNodeMapping) {
        var sha = node.data().sha();
        if (shaNodeMapping.containsKey(sha)) {
            return shaNodeMapping.get(sha);
        }
        var successors = getSuccessors(node).stream()
                .map(successor -> synchronize(successor, shaNodeMapping))
                .collect(Collectors.toSet());
        node.successors(successors);
        return node;
    }

    private CompletableFuture<GitHubContent> getSuccessor(
            String requestURI) {
        return client
                .getResponse(requestURI)
                .thenApplyAsync(response ->
                        Json.decodeValue(response.body(), new TypeReference<>() {
                        }), executor);
    }

    private CompletableFuture<Set<GitHubContent>> getSuccessors(
            String requestURI) {
        return client
                .getResponse(requestURI)
                .thenApplyAsync(response ->
                        Json.decodeValue(response.body(), new TypeReference<>() {
                        }), executor);
    }

    private Set<Node<GitHubContent>> getSuccessors(Node<GitHubContent> node) {
        var type = node.data().type();
        var url = node.data().url();
        switch (type) {
            case "file":
                return Optional.of(getSuccessor(url)
                        .thenApplyAsync(Node::new, executor))
                        .map(CompletableFuture::join)
                        .filter(Predicate.not(node::equals))
                        .stream()
                        .collect(Collectors.toSet());
            case "dir":
                return getSuccessors(url)
                        .thenApplyAsync(gitHubContents -> gitHubContents.parallelStream().map(Node::new).collect(Collectors.toSet()), executor)
                        .join();
            default:
                return Collections.emptySet();
        }
    }

}
