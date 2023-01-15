package com.learntocodetogether.retrybackoff;

import com.learntocodetogether.retrybackoff.server.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExponentialBackoffStrategyTest {

    static int MAX_ATTEMPTS = 5;
    static TimeDelayProvider timeDelayProvider = new ExponentialTimeDelayProvider(2, 10_000);
    static BackoffStrategy exponentialBackoffStrategy = new ExponentialBackoffStrategy(timeDelayProvider);
    static RetrySender retrySender = new RetrySender(exponentialBackoffStrategy, MAX_ATTEMPTS);

    static RequestHandler requestHandler = new RetryRequestHandler();
    static SimpleHttpHandler simpleHttpHandler = new SimpleHttpHandler(requestHandler);
    static SimpleHttpServer simpleHttpServer = new SimpleHttpServer(simpleHttpHandler);


    static RetryRequest succeededFirstTry = new RetryRequest(UUID.randomUUID().toString(), 1);
    static RetryRequest succeededMoreTries = new RetryRequest(UUID.randomUUID().toString(), 3);
    static RetryRequest failOnAllTries = new RetryRequest(UUID.randomUUID().toString(), -1);

    static String SUCCESS = "SUCCESS";
    static String FAILURE = "FAILURE";

    @BeforeAll
    static void init() throws IOException {
        simpleHttpServer.run();
    }

    @Test
    void testSuccessfulFirstTry() {
        String status = retrySender.getStatus(succeededFirstTry.uuid(), succeededFirstTry.successWhen());
        assertEquals(1, requestHandler.getTries(succeededFirstTry.uuid()));
        assertEquals(SUCCESS, status);
    }


    @Test
    void testSuccessMoreTries() {
        String status = retrySender.getStatus(succeededMoreTries.uuid(), succeededMoreTries.successWhen());
        assertEquals(3, requestHandler.getTries(succeededMoreTries.uuid()));
        assertEquals(SUCCESS, status);
    }

    @Test
    void testAllFails() {
        String status = retrySender.getStatus(failOnAllTries.uuid(), failOnAllTries.successWhen());
        assertEquals(MAX_ATTEMPTS, requestHandler.getTries(failOnAllTries.uuid()));
        assertEquals(FAILURE, status);
    }


    @Test
    void test() {
        String uuid = UUID.randomUUID().toString();
        int succeedOn = 3;
        String status = retrySender.getStatus(uuid, succeedOn);
        assertEquals("SUCCESS", status);
    }


    @Test
    void testDelay() {
        long delay1 = timeDelayProvider.getDelay(2);
        assertTrue(delay1 >= 4000 && delay1 <= 5000);
        long delay2 = timeDelayProvider.getDelay(5);
        assertEquals(delay2, timeDelayProvider.maxBackoff());
        long delay3 = timeDelayProvider.getDelay(3);
        assertTrue(delay3 >= 8000 && delay3 <= 9000);
    }
}