package com.learntocodetogether.executor;

import lombok.Getter;
import lombok.ToString;

@ToString
public record RepoInfo(String owner, String repoName, int noStars, String description) { }