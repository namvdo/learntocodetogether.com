package com.learntocodetogether.retrybackoff;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author namvdo
 */
public class ExponentialTimeDelayProvider implements TimeDelayProvider {
    private final ThreadLocalRandom rand = ThreadLocalRandom.current();
    private final int base;
    private final long maxBackOffTime;

    public ExponentialTimeDelayProvider(int base, long maxBackOffTime) {
        this.base = base;
        this.maxBackOffTime = maxBackOffTime;
    }

    @Override
    public long getDelay(int noAttempts) {
        double pow = Math.pow(base, noAttempts);
        int extraDelay = rand.nextInt(1000);
        return (long) Math.min(pow * 1000 + extraDelay, maxBackOffTime);
    }

    @Override
    public long maxBackoff() {
        return this.maxBackOffTime;
    }
}
