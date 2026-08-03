package com.adl.et.telco.mvno.productcatalog.application.transport.response.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Response of the schema validation API.
 *
 * <p>The API answers 200 for a document that breaks its schema and for a schema that is not
 * loaded, the outcome is carried by {@code valid} and {@code schemaFound}. Only a malformed
 * request is answered with an error status.</p>
 */
@Data
public class SchemaValidationResponseEntity {

    @JsonProperty("valid")
    private boolean valid;

    @JsonProperty("schemaFound")
    private boolean schemaFound;

    @JsonProperty("schemaType")
    private String schemaType;

    @JsonProperty("name")
    private String name;

    @JsonProperty("errors")
    private List<String> errors;
}
