package com.rafaelbaetapena;

import com.rafaelbaetapena.quotes.external.ExternalQuote;
import com.rafaelbaetapena.quotes.external.ExternalQuoteProducer;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class EventScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(EventScheduler.class);
    private static final List<String> SYMBOLS = Arrays.asList("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA");
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private final ExternalQuoteProducer externalQuoteProducer;

    public EventScheduler(final ExternalQuoteProducer externalQuoteProducer) {
        this.externalQuoteProducer = externalQuoteProducer;
    }

    @Scheduled(fixedDelay = "10s")
    void generate() {
        final ExternalQuote quote = new ExternalQuote(
                SYMBOLS.get(RANDOM.nextInt(0, SYMBOLS.size() - 1)),
                randomValue(),
                randomValue()
        );
        LOG.debug("Generate external quote {}", quote);
        externalQuoteProducer.send(quote.getSymbol(), quote);
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(RANDOM.nextDouble(0, 1000));
    }
}
