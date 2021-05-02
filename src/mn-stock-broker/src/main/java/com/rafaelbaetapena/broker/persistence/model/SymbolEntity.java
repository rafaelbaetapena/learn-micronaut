package com.rafaelbaetapena.broker.persistence.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "symbol")
@Table(name = "symbols", schema = "mn")
@AllArgsConstructor
@NoArgsConstructor
public class SymbolEntity {

    @Id
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
