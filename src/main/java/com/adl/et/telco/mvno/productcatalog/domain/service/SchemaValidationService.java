package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SchemaEntityRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.dto.SchemaValidationResult;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validates JSON documents against the schemas stored in this catalog.
 *
 * <p>This is the single implementation of schema validation of the product catalog. It backs both
 * the in process validation done while resources are processed and the validation API exposed by
 * {@code SchemaController}, which is what the service catalog calls over REST.</p>
 */
@Service
public class SchemaValidationService {

    private static final String INVALID_VALIDATION_REQUEST_CODE = "INVALID_VALIDATION_REQUEST";

    private final SchemaEntityRepositoryInterface repository;
    private final ObjectMapper objectMapper;

    public SchemaValidationService(SchemaEntityRepositoryInterface repository, ObjectMapper objectMapper) {

        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    /**
     * Validate a document against a stored schema.
     *
     * @param schemaType schema type of the schema. When null or empty the schema is matched by
     *                   name only.
     * @param name       name of the schema.
     * @param data       document to validate.
     * @return Result carrying whether the schema was found and the validation errors.
     */
    public SchemaValidationResult validate(String schemaType, String name, Map<String, Object> data) {

        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new DomainException("Schema name is needed to validate a document",
                    INVALID_VALIDATION_REQUEST_CODE);
        }

        if (Objects.isNull(data)) {
            throw new DomainException("Data is needed to validate a document",
                    INVALID_VALIDATION_REQUEST_CODE);
        }

        Optional<SchemaEntity> schema = Objects.isNull(schemaType) || schemaType.trim().isEmpty()
                ? repository.getByName(name)
                : repository.get(schemaType, name);

        return schema
                .map(found -> SchemaValidationResult.validated(schemaType, name, validate(found, data)))
                .orElseGet(() -> SchemaValidationResult.schemaNotFound(schemaType, name));
    }

    private List<String> validate(SchemaEntity schema, Map<String, Object> data) {

        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);

        JsonSchema jsonSchema;
        try {
            jsonSchema = schemaFactory.getSchema(objectMapper.writeValueAsString(schema.getSchema()));
        } catch (JsonProcessingException e) {
            throw new DomainException("Could not read schema " + schema.getName());
        }

        try {
            Set<ValidationMessage> messages = jsonSchema
                    .validate(objectMapper.readTree(objectMapper.writeValueAsString(data)));

            return messages.stream()
                    .map(message -> message.getPath() + " - " + message.getMessage())
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new DomainException("Could not read json validated against schema " + schema.getName());
        }
    }
}
