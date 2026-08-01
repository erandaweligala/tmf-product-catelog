package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.mvno.productcatalog.domain.boundary.SubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.BaseSubResourceDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public abstract class AbstractSubResourceRepository<T extends BaseSubResourceDocument>
        implements SubResourceRepositoryInterface<T> {

    private final MongoTemplate mongoTemplate;
    private final Class<T> clazz;

    protected AbstractSubResourceRepository(MongoTemplate mongoTemplate, Class<T> clazz) {
        this.mongoTemplate = mongoTemplate;
        this.clazz = clazz;
    }

    @Override
    public T save(T entity) {

        return mongoTemplate.save(entity);
    }

    @Override
    public boolean exists(String id) {

        Query query = new Query().addCriteria(Criteria.where("id").is(id));

        return mongoTemplate.exists(query, clazz);
    }
}
