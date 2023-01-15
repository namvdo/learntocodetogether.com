package com.learntocodetogether.executor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author namvdo
 */
public class GithubRepoService implements RepoService {
    private static final Logger logger = LoggerFactory.getLogger(GithubRepoService.class);
    public static final String LIST_REPO_ENDPOINT = "https://api.github.com/users/%s/repos";
    @Override
    public List<String> getReposByUser(String username) {
        List<String> urls = new ArrayList<>();
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()
        ) {
            HttpGet httpGet = new HttpGet(LIST_REPO_ENDPOINT.formatted(username));
            httpGet.setHeader("Accept", "application/vnd.github+json");
            httpGet.setHeader("Authorization", "Bearer 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");
            httpGet.setHeader("X-GitHub-Api-Version", "2022-11-28");
            try (CloseableHttpResponse closeableResponse = closeableHttpClient.execute(httpGet)) {
                String response = new String(closeableResponse.getEntity().getContent().readAllBytes());
                JsonArray arrResponse = JsonParser.parseString(response).getAsJsonArray();
                for(final var resp : arrResponse) {
                    JsonObject respObj = resp.getAsJsonObject();
                    String url = respObj.get("url").getAsString();
                    urls.add(url);
                }
            }
            return urls;
        } catch (Exception e) {
            logger.error("Error while fetching repo urls: {}", e.getMessage(), e);
            return List.of();
        }
    }
}
