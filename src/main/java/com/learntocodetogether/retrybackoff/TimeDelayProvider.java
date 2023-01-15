package com.learntocodetogether.retrybackoff;

/**
 * @author namvdo
 */
public interface TimeDelayProvider {
    long getDelay(int noAttempts);
    long maxBackoff();
}
