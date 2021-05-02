package com.rafaelbaetapena.broker;

import com.rafaelbaetapena.broker.model.Symbol;
import com.rafaelbaetapena.broker.persistence.jpa.SymbolEntity;
import com.rafaelbaetapena.broker.persistence.jpa.SymbolsRepository;
import com.rafaelbaetapena.broker.store.InMemoryStore;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/markets")
public class MarketsController {

    private final InMemoryStore store;
    private final SymbolsRepository symbolsRepository;

    public MarketsController(InMemoryStore store, SymbolsRepository symbolsRepository) {
        this.store = store;
        this.symbolsRepository = symbolsRepository;
    }

    @Operation(summary = "Returns all available markets")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Tag(name = "markets")
    @Get("/")
    public List<Symbol> all() {
        return store.getAllSymbols();
    }

    @Operation(summary = "Returns all available markets from database using JPA")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Tag(name = "markets")
    @Get("/jpa")
    public  List<SymbolEntity> allSymbolsViaJPA() {
        return symbolsRepository.findAll();
    }
}
