package com.learntocodetogether.prometheus;

import static com.google.common.base.Preconditions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class CurrencyServiceImpl implements CurrencyService{

    @Override
    public Currencies getCurrencies() {
       return getUpdatedCurrencyRate();
    }

    @Override
    public long calculateCurrencyAge(Currencies currencies) {
        checkNotNull(currencies);
        checkNotNull(currencies.lastUpdated());
        List<Currency> currencyList = checkNotNull(currencies.currencies());
        if (currencyList.isEmpty()) return -1L;
        long now = System.currentTimeMillis();
        long lastUpdate = currencies
                .lastUpdated()
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli();
        return 60 * 60L;
    }

    private Currencies getUpdatedCurrencyRate() {
        List<Currency> currencies = List.of(
                Currency.create("USD", "US Dollar", BigDecimal.ONE),
                Currency.create("VND", "Vietnam Dong", BigDecimal.valueOf(24500)),
                Currency.create("JPY", "Yen", BigDecimal.valueOf(134))
        );
        LocalDateTime updated = LocalDateTime.now();
        return Currencies.create(currencies, updated);
    }


}
