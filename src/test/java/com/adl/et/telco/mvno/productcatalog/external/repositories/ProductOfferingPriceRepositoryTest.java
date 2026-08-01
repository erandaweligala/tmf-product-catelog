package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;

import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductOfferingPriceRepositoryTest {


    ProductOfferingPriceRepository repository;
    MongoTemplate mockMongoTemplate;

    @BeforeEach
    void setUp() {
        mockMongoTemplate = Mockito.mock(MongoTemplate.class);
        repository = new ProductOfferingPriceRepository(mockMongoTemplate);
    }


    @Test
    void getByIdTest() {

        ProductOfferingPrice result = new ProductOfferingPrice();
        result.setId("id");

        Mockito.when(mockMongoTemplate.findById("id", ProductOfferingPrice.class))
                .thenReturn(result);

        Optional<ProductOfferingPrice> actual = repository.get("id");

        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals("id", actual.get().getId());
    }

    @Test
    void saveTest() {

        ProductOfferingPrice toSave = new ProductOfferingPrice();
        toSave.setName("name");

        ProductOfferingPrice saved = new ProductOfferingPrice();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockMongoTemplate.save(Mockito.argThat(value -> value instanceof ProductOfferingPrice &&
                        "name".equals(((ProductOfferingPrice) value).getName()))))
                .thenReturn(saved);

        ProductOfferingPrice result = repository.save(toSave);

        Assertions.assertEquals("id", result.getId());
    }

    @Test
    void deleteTest() {

        Mockito.when(mockMongoTemplate.remove(Mockito.any(Query.class), Mockito.eq(ProductOfferingPrice.class)))
                .thenReturn(Mockito.mock(DeleteResult.class));

        repository.delete("id");

        Mockito.verify(mockMongoTemplate, Mockito.times(1))
                .remove(Mockito.any(Query.class), Mockito.eq(ProductOfferingPrice.class));
    }

}