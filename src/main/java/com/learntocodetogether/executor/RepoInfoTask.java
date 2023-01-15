package com.learntocodetogether.executor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public final class RepoInfoTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RepoInfoTask.class);
    private final Object lock = new Object();
    private final List<RepoInfo> repos;
    private final String endpoint;

    public RepoInfoTask(List<RepoInfo> repos, String endpoint) {
        this.repos = repos;
        this.endpoint = endpoint;
    }

    @Override
    public void run() {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()
        ) {
            logger.info("Current thread: {}", Thread.currentThread().getName());
            HttpGet httpGet = new HttpGet(endpoint);
            httpGet.setHeader("Accept", "application/vnd.github+json");
            httpGet.setHeader("Authorization", "Bearer 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");
            httpGet.setHeader("X-GitHub-Api-Version", "2022-11-28");
            CloseableHttpResponse closeableResponse = closeableHttpClient.execute(httpGet);
            String response = new String(closeableResponse.getEntity().getContent().readAllBytes());
            logger.info("response: {}", response);
            JsonObject jsonResp = JsonParser.parseString(response).getAsJsonObject();
            JsonObject ownerObj = jsonResp.get("owner").getAsJsonObject();
            String owner = ownerObj.get("login").getAsString();
            String repoName = jsonResp.get("name").getAsString();
            int noStars = jsonResp.get("stargazers_count").getAsInt();
            String desc = jsonResp.get("description").getAsString();
            RepoInfo repoInfo = new RepoInfo(owner, repoName, noStars, desc);
            synchronized (lock) {
                repos.add(repoInfo);
            }
        } catch (Exception e) {
            logger.error("Fail to get repo information for url: {}", endpoint);
        }
    }
}