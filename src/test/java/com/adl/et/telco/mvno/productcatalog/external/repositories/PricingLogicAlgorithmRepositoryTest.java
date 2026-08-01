package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;

import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PricingLogicAlgorithmRepositoryTest {



    PricingLogicAlgorithmRepository repository;
    MongoTemplate mockMongoTemplate;


    @BeforeEach
    void setUp() {
        mockMongoTemplate = Mockito.mock(MongoTemplate.class);
        repository = new PricingLogicAlgorithmRepository(mockMongoTemplate);
    }


    @Test
    void getByIdTest() {

        PricingLogicAlgorithm result = new PricingLogicAlgorithm();
        result.setId("id");

        Mockito.when(mockMongoTemplate.findById("id", PricingLogicAlgorithm.class))
                .thenReturn(result);

        Optional<PricingLogicAlgorithm> actual = repository.get("id");

        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals("id", actual.get().getId());
    }

    @Test
    void saveTest() {

        PricingLogicAlgorithm toSave = new PricingLogicAlgorithm();
        toSave.setName("name");

        PricingLogicAlgorithm saved = new PricingLogicAlgorithm();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockMongoTemplate.save(Mockito.argThat(value -> value instanceof PricingLogicAlgorithm &&
                        "name".equals(((PricingLogicAlgorithm) value).getName()))))
                .thenReturn(saved);

        PricingLogicAlgorithm result = repository.save(toSave);

        Assertions.assertEquals("id", result.getId());
    }

    @Test
    void deleteTest() {

        Mockito.when(mockMongoTemplate.remove(Mockito.any(Query.class), Mockito.eq(PricingLogicAlgorithm.class)))
                .thenReturn(Mockito.mock(DeleteResult.class));

        repository.delete("id");

        Mockito.verify(mockMongoTemplate, Mockito.times(1))
                .remove(Mockito.any(Query.class), Mockito.eq(PricingLogicAlgorithm.class));
    }

}