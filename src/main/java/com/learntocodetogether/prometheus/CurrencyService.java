package com.learntocodetogether.prometheus;

public interface CurrencyService {
    Currencies getCurrencies();
    long calculateCurrencyAge(Currencies currencies);
}
