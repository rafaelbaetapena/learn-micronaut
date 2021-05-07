package com.rafaelbaetapena.quotes.external;

import io.micronaut.core.annotation.Introspected;

import java.math.BigDecimal;

@Introspected
public class ExternalQuote {

    private String symbol;
    private BigDecimal lastPrice;
    private BigDecimal volume;

    public ExternalQuote() {

    }

    public ExternalQuote(String symbol, BigDecimal lastPrice, BigDecimal volume) {
        this.symbol = symbol;
        this.lastPrice = lastPrice;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "ExternalQuote{" +
                "symbol='" + symbol + '\'' +
                ", lastPrice=" + lastPrice +
                ", volume=" + volume +
                '}';
    }
}
