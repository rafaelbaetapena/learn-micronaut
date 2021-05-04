package com.rafaelbaetapena.prices;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Flowable;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.List;

@KafkaClient(batch = true)
public interface PriceUpdateProducer {
    
    @Topic("price_update")
    Flowable<RecordMetadata> send(List<PriceUpdate> prices);
}
