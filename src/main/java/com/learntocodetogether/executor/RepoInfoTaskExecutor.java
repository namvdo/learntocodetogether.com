package com.learntocodetogether.executor;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author namvdo
 */
public class RepoInfoTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(RepoInfoTaskExecutor.class);
    private final GithubRepoService githubRepoService;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public RepoInfoTaskExecutor(GithubRepoService githubRepoService) {
        this.githubRepoService = githubRepoService;
    }

    public List<RepoInfo> getRepoInfos(String username) {
        List<RepoInfo> repoInfos = new ArrayList<>();
        List<String> urls = githubRepoService.getReposByUser(username);
        List<Future<?>> futures = new ArrayList<>();
        for (final String url : urls) {
            Future<?> futureRepoInfo = executor.submit(new RepoInfoTask(repoInfos, url));
            futures.add(futureRepoInfo);
        }
        for (final Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Fail to get repo info: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
        return repoInfos;
    }

    public static void main(String[] args) {
        GithubRepoService githubRepoService = new GithubRepoService();
        RepoInfoTaskExecutor repoInfoTaskExecutor = new RepoInfoTaskExecutor(githubRepoService);
        List<RepoInfo> repoInfos = repoInfoTaskExecutor.getRepoInfos("namvdo");
        for(final RepoInfo repo : repoInfos) {
            String repoInfo = String.format("""
                    Owner: %s
                    Repo name: %s
                    No stars: %d
                    Description: %s
                    """, repo.owner(), repo.repoName(), repo.noStars(), repo.description());
            logger.info("{}", repoInfo);
        }
        repoInfoTaskExecutor.executor.shutdown();
    }
}
