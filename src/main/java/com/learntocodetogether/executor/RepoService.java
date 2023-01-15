package com.learntocodetogether.executor;

import java.util.List;

public interface RepoService {
    List<String> getReposByUser(String username);
}
