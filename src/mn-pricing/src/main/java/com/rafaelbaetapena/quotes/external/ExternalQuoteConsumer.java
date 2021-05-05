package com.rafaelbaetapena.quotes.external;

import com.rafaelbaetapena.prices.PriceUpdate;
import com.rafaelbaetapena.prices.PriceUpdateProducer;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@KafkaListener(
        clientId = "mn-pricing-external-quote-consumer",
        groupId = "external-quote-consumer",
        batch = true,
        offsetReset = OffsetReset.EARLIEST
)
public class ExternalQuoteConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalQuoteConsumer.class);
    private final PriceUpdateProducer priceUpdateProducer;

    public ExternalQuoteConsumer(PriceUpdateProducer priceUpdateProducer) {
        this.priceUpdateProducer = priceUpdateProducer;
    }

    @Topic("external-quotes")
    void receive(List<ExternalQuote> externalQuotes) {
        LOG.debug("Consuming batch of external quotes {}", externalQuotes);
        // Forward price updates
        final List<PriceUpdate> priceUpdates = externalQuotes.stream().map(
                quote -> new PriceUpdate(quote.getSymbol(), quote.getLastPrice())
        ).collect(Collectors.toList());
        priceUpdateProducer.send(priceUpdates)
            .doOnError(e -> LOG.error("Failed to produce:", e.getCause()))
            .forEach(recordMetadata ->
                    LOG.debug("Record sent to topic {} on offset {}",
                            recordMetadata.topic(),
                            recordMetadata.offset()));
    }
}
