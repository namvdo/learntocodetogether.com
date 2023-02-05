package com.learntocodetogether.retrybackoff;

import ch.qos.logback.core.joran.sanity.Pair;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface BackoffStrategy {
    /**
     * Tests the supplier value with the provided predicate, if the predicate
     * evaluates to false, then the supplier value is repeatedly provided to the predicate
     * till it's evaluated to true or maximum number of attempts are reached, then
     * return the supplier value wrapped in an Optional, which is potentially an empty optional.
     *
     * @param supplier the action to be performed that potentially can fail
     * @param predicate the predicate to test the supplier value
     * @param numAttempts maximum number of attempts to perform the supplier action
     *
     * @return the supplier value wrapped in an optional, possibly empty.
     * @param <T> return type of the supplier value
     */
    <T> Optional<T> get(Supplier<T> supplier, Predicate<T> predicate, int numAttempts);
}
