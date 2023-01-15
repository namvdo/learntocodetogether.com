package com.learntocodetogether.prometheus;

import java.time.LocalDateTime;
import java.util.List;

public record Currencies(List<Currency> currencies, LocalDateTime lastUpdated) {
    public static Currencies create(List<Currency> currencies, LocalDateTime lastUpdated) {
        return new Currencies(currencies, lastUpdated);
    }
}

