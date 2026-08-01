package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductSpecificationRepositoryTest {

    private ProductSpecificationRepository productSpecificationRepository;

    @BeforeEach
    void setUp() {
        productSpecificationRepository = new ProductSpecificationRepository(null); // Pass null or mock MongoTemplate object
    }

    @Test
    void modifyFilters_shouldModifyBooleanFields() {
        // Arrange
        List<Filter> filters = new ArrayList<>();
        Filter filter1 = new Filter("isBundle", new String[]{"true"},null);
        Filter filter2 = new Filter("productSpecCharacteristic.productSpecCharacteristicValue.isDefault", new String[]{"false"},null);
        filters.add(filter1);
        filters.add(filter2);

        // Act
        productSpecificationRepository.modifyFilters(filters);

        // Assert
        assertArrayEquals(new Object[]{true}, filter1.getValue());
        assertArrayEquals(new Object[]{false}, filter2.getValue());
    }
//    @Test
//    void noFieldNoFilterNoPaginationTest() {
//
//        List<Filter> filters = new ArrayList<>();
//        String fields = "";
//        Pageable pageable = new Pageable();
//
//        AggregationResults<ProductSpecification> mockResult = Mockito.mock(AggregationResults.class);
//
//        Mockito.when(mockMongoTemplate.aggregate(
//                Mockito.argThat(aggregation -> !aggregation.getPipeline().getOperations().isEmpty()
//                        && aggregation.getPipeline().getOperations().get(0) instanceof ProjectionOperation),
//                Mockito.eq(ProductSpecification.class),
//                Mockito.eq(ProductSpecification.class)
//        )).thenReturn(mockResult);
//
//        ProductSpecification result1 = new ProductSpecification();
//        result1.setId("1");
//        ProductSpecification result2 = new ProductSpecification();
//        result2.setId("2");
//
//        Mockito.when(mockResult.getMappedResults()).thenReturn(Arrays.asList(result1, result2));
//
//        Page<ProductSpecification> result = repository.query(filters, fields, pageable);
//
//        Assertions.assertEquals(2, result.getContent().size());
//        Assertions.assertEquals(result1.getId(), result.getContent().get(0).getId());
//        Assertions.assertEquals(result2.getId(), result.getContent().get(1).getId());
//    }
//
//    @Test
//    void fieldFilterPaginationTest() {
//
//        List<Filter> filters = Arrays.asList(
//                new Filter("name", new Object[]{"name"}, FilterOperation.IS),
//                new Filter("description", new Object[]{"description"}, FilterOperation.REGEX),
//                new Filter("productSpecCharacteristic.maxCardinality", new Object[]{"1"}, FilterOperation.REGEX),
//                new Filter("lastUpdate", new Object[]{"2021-06-03T09:59:34.533+05:30"}, FilterOperation.LT)
//        );
//
//        String fields = "name,description,attachment";
//        Pageable pageable = new Pageable(10, 10);
//
//        AggregationResults<ProductSpecification> mockResult = Mockito.mock(AggregationResults.class);
//
//        Mockito.when(mockMongoTemplate.aggregate(
//                Mockito.argThat(aggregation -> aggregation.getPipeline().getOperations().size() == 4 &&
//                        aggregation.getPipeline().getOperations().get(0) instanceof MatchOperation),
//                Mockito.eq(ProductSpecification.class),
//                Mockito.eq(ProductSpecification.class)
//        )).thenReturn(mockResult);
//
//        Mockito.when(mockMongoTemplate.count(Mockito.any(Query.class), Mockito.eq(ProductSpecification.class)))
//                .thenReturn(2L);
//
//        ProductSpecification result1 = new ProductSpecification();
//        result1.setId("1");
//        ProductSpecification result2 = new ProductSpecification();
//        result2.setId("2");
//
//        Mockito.when(mockResult.getMappedResults()).thenReturn(Arrays.asList(result1, result2));
//
//        Page<ProductSpecification> result = repository.query(filters, fields, pageable);
//
//        Assertions.assertEquals(2, result.getContent().size());
//        Assertions.assertEquals(result1.getId(), result.getContent().get(0).getId());
//        Assertions.assertEquals(result2.getId(), result.getContent().get(1).getId());
//        Assertions.assertEquals(2, result.getTotal());
//    }

    @Test
    void modifyFilters_shouldConvertFloatField() {
        // Arrange
        List<Filter> filters = new ArrayList<>();
        Filter filter = new Filter("attachment.size.amount", new String[]{"10.5"},null);
        filters.add(filter);

        // Act
        productSpecificationRepository.modifyFilters(filters);

        // Assert
        assertArrayEquals(new Object[]{10.5f}, filter.getValue());
    }

    @Test
    void modifyFilters_shouldConvertIntegerField() {
        // Arrange
        List<Filter> filters = new ArrayList<>();
        Filter filter = new Filter("productSpecCharacteristic.maxCardinality", new String[]{"5"},null);
        filters.add(filter);

        // Act
        productSpecificationRepository.modifyFilters(filters);

        // Assert
        assertArrayEquals(new Object[]{5}, filter.getValue());
    }

    @Test
    void modifyFilters_shouldNotModifyNonMatchingFields() {
        // Arrange
        List<Filter> filters = new ArrayList<>();
        Filter filter = new Filter("otherField", new String[]{"value"},null);
        filters.add(filter);

        // Act
        productSpecificationRepository.modifyFilters(filters);

        // Assert
        assertArrayEquals(new Object[]{"value"}, filter.getValue());
    }

    // Add more test cases to cover other scenarios and edge cases

}
