package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.mvno.productcatalog.domain.boundary.JsonSubResourceRepositoryInterface;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonSubResourceRepository implements JsonSubResourceRepositoryInterface {

    private final MongoTemplate mongoTemplate;

    public JsonSubResourceRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Map<String, Object> save(String name, Map<String, Object> json) {

        Map<String, Object> saved = mongoTemplate.save(json, name);

        modifyIdField(saved);

        return saved;
    }

    @Override
    public Map<String, Object> get(String name, String id) {

        Map<String, Object> result = mongoTemplate.findById(id, Map.class, name);

        if (result == null) {
            return null;
        }

        modifyIdField(result);

        return result;
    }

    @Override
    public boolean exists(String name, String id) {

        Query query = new Query().addCriteria(Criteria.where("_id").is(id));

        return mongoTemplate.exists(query, name);
    }

    private void modifyIdField(Map<String, Object> entity) {

        entity.put("id", entity.get("_id").toString());
        entity.remove("_id");
    }
}
