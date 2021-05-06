package com.rafaelbaetapena.websockets.simple;

import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.websocket.WebSocketBroadcaster;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class PricePush {

    private final WebSocketBroadcaster broadcaster;

    public PricePush(WebSocketBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Scheduled(fixedDelay = "5s")
    public void push(){
        broadcaster.broadcastSync(
                new PriceUpdate("AMZN", randomValue())
        );
    }

    private BigDecimal randomValue() {
        return  BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1500, 2000));
    }
}
