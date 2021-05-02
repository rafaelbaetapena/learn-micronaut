package com.rafaelbaetapena.data.jdbc;

import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class DummyDataProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DummyDataProvider.class);
    private final TransactionsRepository transactions;

    public DummyDataProvider(TransactionsRepository transactions) {
        this.transactions = transactions;
    }

    @Scheduled(fixedDelay = "1s")
    void generate() {
        var transaction = new Transaction(UUID.randomUUID().toString(), "SYMBOL", randomValue());
        transactions.save(transaction);
        LOG.info("Content {}", transactions.findAll().size());
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
    }
}
