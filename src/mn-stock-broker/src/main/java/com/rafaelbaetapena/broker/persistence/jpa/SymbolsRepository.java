package com.rafaelbaetapena.broker.persistence.jpa;

import com.rafaelbaetapena.broker.persistence.model.SymbolEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface SymbolsRepository extends CrudRepository<SymbolEntity, String> {

    @Override
    List<SymbolEntity> findAll();
}
