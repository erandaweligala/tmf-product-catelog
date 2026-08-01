package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRepositoryTest {

    MongoTemplate mockMongoTemplate;

    CategoryRepository mockCategoryRepository;

    @BeforeEach
    void setup(){

        mockMongoTemplate = Mockito.mock(MongoTemplate.class);

        mockCategoryRepository = new CategoryRepository(mockMongoTemplate, Runnable::run);

    }

    @Test
    void getByIdTest() {

        Category result = new Category();
        result.setId("id");

        Mockito.when(mockMongoTemplate.findById("id", Category.class))
                .thenReturn(result);

        Optional<Category> actual = mockCategoryRepository.get("id");

        assertTrue(actual.isPresent());
        assertEquals("id", actual.get().getId());
    }

    @Test
    void saveTest() {

        Category toSave = new Category();
        toSave.setName("name");

        Category saved = new Category();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockMongoTemplate.save(Mockito.argThat(value -> value instanceof Category &&
                        "name".equals(((Category) value).getName()))))
                .thenReturn(saved);

        Category result = mockCategoryRepository.save(toSave);

        Assertions.assertEquals("id", result.getId());
    }

    @Test
    void deleteTest() {

        Mockito.when(mockMongoTemplate.remove(Mockito.any(Query.class), Mockito.eq(Category.class)))
                .thenReturn(Mockito.mock(DeleteResult.class));

        mockCategoryRepository.delete("id");

        Mockito.verify(mockMongoTemplate, Mockito.times(1))
                .remove(Mockito.any(Query.class), Mockito.eq(Category.class));
    }


}