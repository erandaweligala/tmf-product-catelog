package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SchemaEntityRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.dto.SchemaValidationResult;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class SchemaValidationServiceTest {

    private SchemaEntityRepositoryInterface mockRepository;

    private SchemaValidationService service;

    @BeforeEach
    void setUp() {

        mockRepository = Mockito.mock(SchemaEntityRepositoryInterface.class);

        service = new SchemaValidationService(mockRepository, new ObjectMapper());
    }

    @Test
    void validateValidDocumentTest() {

        Mockito.when(mockRepository.get("ValueType", "MobileService"))
                .thenReturn(Optional.of(schema()));

        Map<String, Object> data = new HashMap<>();
        data.put("msisdn", "0771234567");

        SchemaValidationResult result = service.validate("ValueType", "MobileService", data);

        Assertions.assertTrue(result.isSchemaFound());
        Assertions.assertTrue(result.isValid());
        Assertions.assertTrue(result.getErrors().isEmpty());
        Assertions.assertEquals("ValueType", result.getSchemaType());
        Assertions.assertEquals("MobileService", result.getName());
    }

    @Test
    void validateInvalidDocumentTest() {

        Mockito.when(mockRepository.get("ValueType", "MobileService"))
                .thenReturn(Optional.of(schema()));

        Map<String, Object> data = new HashMap<>();
        data.put("msisdn", 771234567);

        SchemaValidationResult result = service.validate("ValueType", "MobileService", data);

        Assertions.assertTrue(result.isSchemaFound());
        Assertions.assertFalse(result.isValid());
        Assertions.assertFalse(result.getErrors().isEmpty());
    }

    @Test
    void validateMissingRequiredFieldTest() {

        Mockito.when(mockRepository.get("ValueType", "MobileService"))
                .thenReturn(Optional.of(schema()));

        SchemaValidationResult result = service.validate("ValueType", "MobileService", new HashMap<>());

        Assertions.assertTrue(result.isSchemaFound());
        Assertions.assertFalse(result.isValid());
        Assertions.assertFalse(result.getErrors().isEmpty());
    }

    @Test
    void validateByNameOnlyTest() {

        Mockito.when(mockRepository.getByName("MobileService"))
                .thenReturn(Optional.of(schema()));

        Map<String, Object> data = new HashMap<>();
        data.put("msisdn", "0771234567");

        SchemaValidationResult result = service.validate(null, "MobileService", data);

        Assertions.assertTrue(result.isValid());

        Mockito.verify(mockRepository, Mockito.times(1)).getByName("MobileService");
        Mockito.verify(mockRepository, Mockito.never()).get(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    void validateWithoutStoredSchemaTest() {

        Mockito.when(mockRepository.get("ValueType", "MobileService"))
                .thenReturn(Optional.empty());

        SchemaValidationResult result = service.validate("ValueType", "MobileService", new HashMap<>());

        Assertions.assertFalse(result.isSchemaFound());
        Assertions.assertFalse(result.isValid());
    }

    @Test
    void validateWithoutNameTest() {

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> service.validate("ValueType", " ", new HashMap<>())
        );

        Assertions.assertEquals("INVALID_VALIDATION_REQUEST", thrown.getCode());
    }

    @Test
    void validateWithoutDataTest() {

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> service.validate("ValueType", "MobileService", null)
        );

        Assertions.assertEquals("INVALID_VALIDATION_REQUEST", thrown.getCode());
    }

    private SchemaEntity schema() {

        Map<String, Object> msisdn = new HashMap<>();
        msisdn.put("type", "string");

        Map<String, Object> properties = new HashMap<>();
        properties.put("msisdn", msisdn);

        Map<String, Object> body = new HashMap<>();
        body.put("type", "object");
        body.put("properties", properties);
        body.put("required", Collections.singletonList("msisdn"));

        SchemaEntity schema = new SchemaEntity();
        schema.setSchemaType("ValueType");
        schema.setName("MobileService");
        schema.setSchema(body);

        return schema;
    }
}
