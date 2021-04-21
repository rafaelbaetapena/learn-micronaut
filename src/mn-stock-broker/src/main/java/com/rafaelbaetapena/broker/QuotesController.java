package com.rafaelbaetapena.broker;

import com.rafaelbaetapena.broker.model.Quote;
import com.rafaelbaetapena.broker.store.InMemoryStore;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

import java.util.Optional;

@Controller("/quotes")
public class QuotesController {

    private final InMemoryStore store;

    public QuotesController(InMemoryStore store) {
        this.store = store;
    }

    @Get("/{symbol}")
    public HttpResponse getQuote(@PathVariable String symbol) {
        Optional<Quote> maybeQuote = store.fetchQuote(symbol);
        return  HttpResponse.ok(maybeQuote.get());
    }
}
