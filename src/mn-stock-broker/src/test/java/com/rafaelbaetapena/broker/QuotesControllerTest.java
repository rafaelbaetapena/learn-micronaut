package com.rafaelbaetapena.broker;

import com.rafaelbaetapena.broker.model.Quote;
import com.rafaelbaetapena.broker.model.Symbol;
import com.rafaelbaetapena.broker.store.InMemoryStore;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import static io.micronaut.http.HttpRequest.GET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class QuotesControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(QuotesControllerTest.class);
    private final ThreadLocalRandom current = ThreadLocalRandom.current();

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    InMemoryStore store;

    @Test
    void returnsQuotesPerSymbol() {
        final Quote apple = initRandomQuote("APPL");
        store.update(apple);

        final Quote amazon = initRandomQuote("AMZN");
        store.update(amazon);

        final Quote appleResult = client.toBlocking().retrieve(GET("/quotes/APPL"), Quote.class);
        LOG.debug("Result: {}", appleResult);
        assertThat(apple).isEqualToComparingFieldByField(appleResult);

        final Quote amazonResult = client.toBlocking().retrieve(GET("/quotes/AMZN"), Quote.class);
        LOG.debug("Result: {}", amazonResult);
        assertThat(amazon).isEqualToComparingFieldByField(amazonResult);
    }

    private Quote initRandomQuote(String symbolValue) {
        return Quote.builder()
                .symbol(new Symbol(symbolValue))
                .bid(randomValue())
                .ask(randomValue())
                .lastPrice(randomValue())
                .volume(randomValue())
                .build();
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(current.nextDouble(1, 100));
    }

}
