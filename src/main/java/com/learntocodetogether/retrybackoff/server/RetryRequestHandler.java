package com.learntocodetogether.retrybackoff.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author namvdo
 */
public class RetryRequestHandler implements RequestHandler {
    private final Map<String, Integer> idToRequests = new ConcurrentHashMap<>();
    private final Object lock = new Object();
    @Override
    public String handle(RetryRequest request) {
        String uuid = request.uuid();
        synchronized (lock) {
            if (idToRequests.get(uuid) == null) {
                idToRequests.put(uuid, 1);
            } else {
                int numRequests = idToRequests.get(uuid);
                idToRequests.put(uuid, ++numRequests);
            }
        }
        int numRequests = idToRequests.get(uuid);
        if (numRequests >= request.successWhen() && request.successWhen() != -1) {
            return "SUCCESS";
        } else {
            return "FAILURE";
        }
    }

    @Override
    public int getTries(String uuid) {
        if (this.idToRequests.get(uuid) == null) return 0;
        return this.idToRequests.get(uuid);
    }
}
