package com.adl.et.telco.mvno.productcatalog.domain.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Outcome of validating a JSON document against a stored schema.
 *
 * <p>A missing schema is reported with {@code schemaFound = false} instead of an exception, so
 * that the callers of the validation API can tell a document that breaks its schema apart from a
 * schema that is not loaded yet.</p>
 */
@Data
public class SchemaValidationResult {

    private boolean schemaFound;

    private boolean valid;

    private String schemaType;

    private String name;

    private List<String> errors = Collections.emptyList();

    /**
     * Result for a schema that is not stored in the catalog.
     *
     * @param schemaType schema type that was looked up, null when the lookup was by name only.
     * @param name       name of the schema that was looked up.
     * @return Result with both {@code schemaFound} and {@code valid} false.
     */
    public static SchemaValidationResult schemaNotFound(String schemaType, String name) {

        SchemaValidationResult result = new SchemaValidationResult();

        result.setSchemaFound(false);
        result.setValid(false);
        result.setSchemaType(schemaType);
        result.setName(name);

        return result;
    }

    /**
     * Result of a document that was validated against a stored schema.
     *
     * @param schemaType schema type of the matched schema.
     * @param name       name of the matched schema.
     * @param errors     validation errors, empty when the document is valid.
     * @return Result with {@code schemaFound} true and {@code valid} set from the errors.
     */
    public static SchemaValidationResult validated(String schemaType, String name, List<String> errors) {

        SchemaValidationResult result = new SchemaValidationResult();

        result.setSchemaFound(true);
        result.setValid(errors.isEmpty());
        result.setSchemaType(schemaType);
        result.setName(name);
        result.setErrors(errors);

        return result;
    }
}
