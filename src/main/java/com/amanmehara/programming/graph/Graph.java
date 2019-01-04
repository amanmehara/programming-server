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

import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T> {

    private Set<Node<T>> roots;
    private Set<Node<T>> nodes;
    private Set<Edge> edges;

    public Graph(Set<Node<T>> roots) {
        this.roots = roots;
        this.nodes = roots;
    }

    public boolean addNode(Node<T> node) {
        return nodes.add(node);
    }

    public boolean removeNode(Node<T> node) {
        if (!nodes.contains(node)) {
            throw new IllegalStateException();
        }
        node.inEdges().forEach(this::removeEdge);
        node.outEdges().forEach(this::removeEdge);
        return nodes.remove(node);
    }

    public void addEgde(Node<T> sourceNode, Node<T> destinationNode) {
        if (!nodes.contains(sourceNode) || !nodes.contains(destinationNode)) {
            throw new IllegalStateException();
        }
        Edge<T> edge = new Edge<>(sourceNode, destinationNode);
        edges.add(edge);
    }

    public void removeEdge(Edge<T> edge) {
        if (!edges.contains(edge)) {
            throw new IllegalStateException();
        }
        edge.sourceNode().removeOutEdge(edge);
        edge.destinationNode().removeInEdge(edge);
        edges.remove(edge);
    }

    public Set<Node> predecessors(Node<T> node) {
        if (!nodes.contains(node)) {
            throw new IllegalStateException();
        }
        return node.inEdges().parallelStream()
                .map(Edge::sourceNode)
                .collect(Collectors.toSet());
    }

    public Set<Node> successors(Node<T> node) {
        if (!nodes.contains(node)) {
            throw new IllegalStateException();
        }
        return node.outEdges().parallelStream()
                .map(Edge::destinationNode)
                .collect(Collectors.toSet());
    }

}
