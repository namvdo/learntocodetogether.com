package com.learntocodetogether.prometheus;

import io.prometheus.client.Gauge;
import jakarta.ws.rs.Path;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
public class CurrencyStatusChecker {
    private ScheduledThreadPoolExecutor scheduleThreadPool = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
    private final CurrencyServiceImpl currencyService = new CurrencyServiceImpl();
    private static final Gauge LATEST_CURRENCY_AGE = Gauge.build()
            .name("latest_currency_age")
            .labelNames("from_rate")
            .help("The latest currency age value")
            .create()
            .register();
    private final Currencies currencies = currencyService.getCurrencies();
    void checkUpdate() {
        scheduleThreadPool.scheduleWithFixedDelay(() -> {
            long currency = currencyService.calculateCurrencyAge(currencies);
            LATEST_CURRENCY_AGE.labels("USD").set(currency);
        }, 10, 10, TimeUnit.SECONDS);
    }

    @GetMapping(value = "/metrics", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getMetrics() {
        checkUpdate();
        StringBuilder sb = new StringBuilder();
        double val = LATEST_CURRENCY_AGE.labels("USD").get();
        sb.append("latest_currency_age").append("{")
                .append("from_rate=")
                .append("\"")
                .append("USD")
                .append("\"")
                .append("}")
                .append(val)
                .append("\n");
        return sb.toString();
    }
}
