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

package com.amanmehara.programming.repository;

import com.amanmehara.programming.graph.DirectedAcyclicGraph;
import com.amanmehara.programming.model.GitHubCache;

import java.io.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CacheRepository {

    private static final Logger LOGGER = Logger.getLogger(CacheRepository.class.getName());
    private final String fileName;

    public CacheRepository(String fileName) {
        this.fileName = fileName;
    }

    public Optional<GitHubCache> fetch() {
        try (var fileInputStream = new FileInputStream(fileName);
             var bufferedInputStream = new BufferedInputStream(fileInputStream);
             var objectInputStream = new ObjectInputStream(bufferedInputStream)
        ) {
            var object = objectInputStream.readObject();
            if (object instanceof DirectedAcyclicGraph) {
                var dag = (DirectedAcyclicGraph) object;
                var cache = new GitHubCache.GitHubCacheBuilder().dag(dag).build();
                return Optional.of(cache);
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return Optional.empty();
    }

    public boolean persist(GitHubCache cache) {
        var dag = cache.dag();

        try (var fileOutputStream = new FileOutputStream(fileName);
             var bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
             var objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {
            objectOutputStream.writeObject(dag);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return false;
        }
    }

}
