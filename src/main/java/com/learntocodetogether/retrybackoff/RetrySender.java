package com.learntocodetogether.retrybackoff;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Optional;

/**
 * @author namvdo
 */
public class RetrySender {
    private static final Logger log = LoggerFactory.getLogger(RetrySender.class);
    private static final String API_ENDPOINT = "http://127.0.0.1:8080/retry";

    private static final CloseableHttpClient client = HttpClientBuilder.create().build();
    private final BackoffStrategy backOffStrategy;
    private final int maxAttempts;

    public RetrySender(BackoffStrategy backOffStrategy, int maxAttempts) {
        this.backOffStrategy = backOffStrategy;
        this.maxAttempts = maxAttempts;
    }

    public String getStatus(String uuid, int successWhen) {
        String endpoint = String.format("%s?uuid=%s&succeedWhen=%d", API_ENDPOINT, uuid, successWhen);
        Optional<String> maybeResponse = backOffStrategy.get(() -> sendRequest(endpoint), "SUCCESS"::equals, maxAttempts);
        return maybeResponse.orElse("FAILURE");
    }

    private String sendRequest(String endpoint) {
        HttpGet httpGet = new HttpGet(endpoint);
        try (CloseableHttpResponse execute = client.execute(httpGet)) {
            InputStream is = execute.getEntity().getContent();
            return new String(is.readAllBytes());
        } catch (Exception e) {
            log.error("Fail to execute request to: {}", endpoint, e);
            return "FAILURE";
        }
    }
}
