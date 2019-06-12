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

package com.amanmehara.programming.graph;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class DirectedAcyclicGraph<T> {

    private Set<Node<T>> roots;

    public DirectedAcyclicGraph(Set<Node<T>> roots) {
        this.roots = roots;
    }

    public Set<Node<T>> roots() {
        return roots;
    }

    public Set<Node<T>> traverse(UnaryOperator<Node<T>> operator) {
        return roots
                .stream()
                .map(root -> dfs(root, operator))
                .collect(Collectors.toSet());
    }

    public Node<T> dfs(Node<T> node, UnaryOperator<Node<T>> operator) {
        node = operator.apply(node);
        Set<Node<T>> successors = node.successors()
                .stream()
                .map(successor -> dfs(successor, operator))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        node.successors(successors);
        return node;
    }

}
