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

package com.amanmehara.programming.model;

import com.amanmehara.programming.graph.Node;
import com.amanmehara.programming.graph.DirectedAcyclicGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class GitHubCache {

    private final DirectedAcyclicGraph<GitHubContent> dag;
    private final Map<Integer, Node<GitHubContent>> nodes;

    private GitHubCache(DirectedAcyclicGraph<GitHubContent> dag,
                        Map<Integer, Node<GitHubContent>> nodes) {
        this.dag = dag;
        this.nodes = nodes;
    }

    public DirectedAcyclicGraph<GitHubContent> dag() {
        return dag;
    }

    public Map<Integer, Node<GitHubContent>> nodes() {
        return nodes;
    }

    public static class GitHubCacheBuilder {

        private DirectedAcyclicGraph<GitHubContent> dag;
        private Map<Integer, Node<GitHubContent>> nodes;

        public GitHubCacheBuilder dag(DirectedAcyclicGraph<GitHubContent> dag) {
            this.dag = dag;
            nodes();
            return this;
        }

        private void nodes() {
            nodes = new HashMap<>();
            UnaryOperator<Node<GitHubContent>> operator = node -> {
                nodes.put(node.hashCode(), node);
                return node;
            };
            dag.traverse(operator);
        }

        public GitHubCache build() {
            return new GitHubCache(dag, nodes);
        }
    }
}
