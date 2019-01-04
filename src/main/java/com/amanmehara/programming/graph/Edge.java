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

import java.util.Objects;

public class Edge<T> {

    private final Node<T> sourceNode;
    private final Node<T> destinationNode;

    public Edge(Node<T> sourceNode, Node<T> destinationNode) {
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
    }

    public Node<T> sourceNode() {
        return sourceNode;
    }

    public Node<T> destinationNode() {
        return destinationNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return sourceNode.equals(edge.sourceNode) &&
                destinationNode.equals(edge.destinationNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNode, destinationNode);
    }

}
