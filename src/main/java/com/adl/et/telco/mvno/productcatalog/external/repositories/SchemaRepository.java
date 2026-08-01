package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.mvno.productcatalog.domain.entities.Schema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SchemaRepository extends MongoRepository<Schema, String> {

    Optional<Schema> findOneByName(String name);
}
