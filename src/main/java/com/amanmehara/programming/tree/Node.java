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

package com.amanmehara.programming.tree;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node<T> {

    private final T data;
    private Set<Node<T>> successors;

    public Node(T data) {
        this.data = data;
        successors = new HashSet<>();
    }

    public T data() {
        return data;
    }

    public Set<Node<T>> successors() {
        return successors;
    }

    public void successors(Set<Node<T>> successors) {
        this.successors = successors;
    }

    public void addSuccessor(Node<T> successor) {
        successors.add(successor);
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
