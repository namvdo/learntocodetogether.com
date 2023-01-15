package com.learntocodetogether.retrybackoff;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author namvdo
 */
public class ExponentialBackoffStrategy implements BackoffStrategy {
    private static final Logger log = LoggerFactory.getLogger(ExponentialBackoffStrategy.class);
    private final TimeDelayProvider timeDelayProvider;

    public ExponentialBackoffStrategy(TimeDelayProvider timeDelayProvider) {
        this.timeDelayProvider = timeDelayProvider;
    }

    @Override
    public <T> Optional<T> get(Supplier<T> supplier, Predicate<T> predicate, int maxAttempts) {
        T t = supplier.get();
        int attempts = 1;
        while (!predicate.test(t) && attempts < maxAttempts) {
            try {
                long time = timeDelayProvider.getDelay(attempts);
                log.info("Predicate tested fail!! Retry in: {}ms", time);
                Thread.sleep(time);
                t = supplier.get();
            } catch (Exception e) {
                log.error("Fail to get the result: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
                return Optional.empty();
            } finally {
                ++attempts;
            }
        }
        log.info("Total requests have tried: {}", attempts);
        return Optional.of(t);
    }

}
