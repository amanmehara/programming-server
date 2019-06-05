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

import com.amanmehara.programming.tree.Node;
import com.amanmehara.programming.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class GitHubCache {

    private final Tree<GitHubContent> tree;
    private final Map<String, Node<GitHubContent>> nodes;

    private GitHubCache(Tree<GitHubContent> tree,
                        Map<String, Node<GitHubContent>> nodes) {
        this.tree = tree;
        this.nodes = nodes;
    }

    public Tree<GitHubContent> tree() {
        return tree;
    }

    public Map<String, Node<GitHubContent>> nodes() {
        return nodes;
    }

    public static class GitHubCacheBuilder {

        private Tree<GitHubContent> tree;
        private Map<String, Node<GitHubContent>> nodes;

        public GitHubCacheBuilder tree(Tree<GitHubContent> tree) {
            this.tree = tree;
            nodes();
            return this;
        }

        private void nodes() {
            nodes = new HashMap<>();
            UnaryOperator<Node<GitHubContent>> operator = node -> {
                nodes.put(node.data().sha(), node);
                return node;
            };
            tree.traverse(operator);
        }

        public GitHubCache build() {
            return new GitHubCache(tree, nodes);
        }
    }
}
