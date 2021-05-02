package com.rafaelbaetapena.broker.persistence.jpa;

import com.rafaelbaetapena.broker.persistence.model.QuoteEntity;
import com.rafaelbaetapena.broker.persistence.model.SymbolEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuotesRepository extends CrudRepository<QuoteEntity, Integer> {

    @Override
    List<QuoteEntity> findAll();

    Optional<QuoteEntity> findBySymbol(SymbolEntity entity);
}
