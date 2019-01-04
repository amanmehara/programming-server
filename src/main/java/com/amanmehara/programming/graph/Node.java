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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node<T> {

    private final T data;
    private Set<Edge> inEdges;
    private Set<Edge> outEdges;

    public Node(T data) {
        this.data = data;
        this.inEdges = new HashSet<>();
    }

    public Node(T data, Set<Edge> inEdges) {
        this.data = data;
        this.inEdges = inEdges;
    }

    public Set<Edge> inEdges() {
        return inEdges;
    }

    public void inEdges(Set<Edge> inEdges) {
        this.inEdges = inEdges;
    }

    public Set<Edge> outEdges() {
        return outEdges;
    }

    public void outEdges(Set<Edge> outEdges) {
        this.outEdges = outEdges;
    }

    public void addInEdge(Edge edge) {
        inEdges.add(edge);
    }

    public void removeInEdge(Edge edge) {
        inEdges.remove(edge);
    }

    public void addOutEdge(Edge edge) {
        outEdges.add(edge);
    }

    public void removeOutEdge(Edge edge) {
        outEdges.remove(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return data.equals(node.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

}
