package com.learntocodetogether.retrybackoff.server;

public interface RequestHandler {
    String handle(RetryRequest request);
    int getTries(String uuid);
}
