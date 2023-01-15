package com.learntocodetogether.retrybackoff.server;

/**
 * @author namvdo
 */
public record RetryRequest(String uuid, int successWhen) { }
