package com.learntocodetogether.prometheus;

import java.math.BigDecimal;

public record Currency(String code, String name, BigDecimal fromUsdRate) {
    public static Currency create(String code, String name, BigDecimal usdRate) {
        return new Currency(code, name, usdRate);
    }
}
