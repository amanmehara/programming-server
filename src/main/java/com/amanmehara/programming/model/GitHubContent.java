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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubContent {

    private String name;
    private String path;
    private String sha;
    private long size;
    private String url;
    private String type;
    private String content;
    private String encoding;

    @JsonCreator
    public GitHubContent(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "path", required = true) String path,
            @JsonProperty(value = "sha", required = true) String sha,
            @JsonProperty(value = "size", required = true) long size,
            @JsonProperty(value = "url", required = true) String url,
            @JsonProperty(value = "type", required = true) String type,
            @JsonProperty(value = "content", required = false) String content,
            @JsonProperty(value = "encoding", required = false) String encoding) {
        this.name = name;
        this.path = path;
        this.sha = sha;
        this.size = size;
        this.url = url;
        this.type = type;
        this.content = content;
        this.encoding = encoding;
    }

    @JsonProperty(value = "name", required = true)
    public String name() {
        return name;
    }

    @JsonProperty(value = "path", required = true)
    public String path() {
        return path;
    }

    @JsonProperty(value = "sha", required = true)
    public String sha() {
        return sha;
    }

    @JsonProperty(value = "size", required = true)
    public long size() {
        return size;
    }

    @JsonProperty(value = "url", required = true)
    public String url() {
        return url;
    }

    @JsonProperty(value = "type", required = true)
    public String type() {
        return type;
    }

    @JsonProperty(value = "content", required = false)
    public String content() {
        return content;
    }

    @JsonProperty(value = "encoding", required = false)
    public String encoding() {
        return encoding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitHubContent that = (GitHubContent) o;
        return sha.equals(that.sha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha);
    }

}
