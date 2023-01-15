package com.learntocodetogether.retrybackoff;

import ch.qos.logback.core.joran.sanity.Pair;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface BackoffStrategy {
    <T> Optional<T> get(Supplier<T> supplier, Predicate<T> predicate, int numAttempts);
}
