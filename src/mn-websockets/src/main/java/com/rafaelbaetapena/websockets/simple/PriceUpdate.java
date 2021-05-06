package com.rafaelbaetapena.websockets.simple;

import java.math.BigDecimal;

public class PriceUpdate {

    private String symbol;
    private BigDecimal price;

    public PriceUpdate() {
    }

    public PriceUpdate(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PriceUpdate{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
