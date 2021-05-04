package com.rafaelbaetapena.quotes.external;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.core.util.StringUtils;
import org.awaitility.Awaitility;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class TestExternalQuoteProducer {

    private static final Logger LOG = LoggerFactory.getLogger(TestExternalQuoteProducer.class);
    private static final String PROPERTY_NAME = "TestExternalQuoteProducer";

    public static final DockerImageName DOCKER_IMAGE_NAME =
            DockerImageName.parse("confluentinc/cp-kafka:5.4.3");

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DOCKER_IMAGE_NAME);
    private static ApplicationContext context;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    @BeforeAll
    static void start() {
        kafka.start();
        LOG.debug("Bootstrap Servers: {}", kafka.getBootstrapServers());

        context = ApplicationContext.run(
                CollectionUtils.mapOf(
                        "kafka.bootstrap.servers", kafka.getBootstrapServers(),
                        PROPERTY_NAME, StringUtils.TRUE
                ),
                Environment.TEST
        );
    }

    @AfterAll
    static void stop() {
        kafka.stop();
        context.close();
    }

    @Test
    public void producing10RecordsWorks() {
        final ExternalQuoteProducer producer = context.getBean(ExternalQuoteProducer.class);
        IntStream.range(0, 10).forEach(count -> {
            var symbol = "TEST-" + count;
            producer.send(symbol, new ExternalQuote(
                    symbol,
                    randomValue(),
                    randomValue())
            );
        });
        final ExternalQuoteObserver observer = context.getBean(ExternalQuoteObserver.class);
        Awaitility.await().untilAsserted(() -> {
            assertEquals(10, observer.inspected.size());
        });
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(RANDOM.nextDouble(0, 1000));
    }

    @Singleton
    @Requires(env = Environment.TEST)
    @Requires(property = PROPERTY_NAME, value = StringUtils.TRUE)
    static class ExternalQuoteObserver {

        List<ExternalQuote> inspected = new ArrayList<>();

        @KafkaListener(
                offsetReset = OffsetReset.EARLIEST
        )
        @Topic("external-quotes")
        void receive(ExternalQuote externalQuote) {
            LOG.debug("Consumed: {}", externalQuote);
            inspected.add(externalQuote);
        }
    }
}
