package com.rafaelbaetapena.broker.persistence.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "quote")
@Table(name = "quotes", schema = "mn")
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(targetEntity = SymbolEntity.class)
    @JoinColumn(name = "symbol", referencedColumnName = "value")
    private SymbolEntity symbol;
    private BigDecimal bid;
    private BigDecimal ask;
    @Column(name = "last_price")
    private BigDecimal lastPrice;
    private BigDecimal volume;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SymbolEntity getSymbol() {
        return symbol;
    }

    public void setSymbol(SymbolEntity symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
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
}
