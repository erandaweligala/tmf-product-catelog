package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.external.repositories.AbstractResourceRepository;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SchemaEntityRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Executor;

@Component
public class SchemaEntityRepository extends AbstractResourceRepository<SchemaEntity> implements SchemaEntityRepositoryInterface {

    private final MongoTemplate mongoTemplate;

    public SchemaEntityRepository(MongoTemplate mongoTemplate, @Qualifier("queryExecutor") Executor queryExecutor) {

        super(mongoTemplate, SchemaEntity.class, queryExecutor);

        this.mongoTemplate = mongoTemplate;
    }

    public Optional<SchemaEntity> get(String schemaType, String name) {

        Query query = new Query().addCriteria(
                new Criteria().andOperator(
                        Criteria.where("schemaType").is(schemaType),
                        Criteria.where("name").is(name)
                )
        );

        return Optional.ofNullable(mongoTemplate.findOne(query, SchemaEntity.class));
    }
}
