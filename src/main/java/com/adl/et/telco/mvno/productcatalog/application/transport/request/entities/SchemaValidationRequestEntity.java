package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Request of the schema validation API.
 *
 * <p>{@code schemaType} and {@code name} are optional. When they are not sent they are taken from
 * the {@code @baseType} and {@code @type} of the document, which is how the catalogs name the
 * schema of a characteristic value.</p>
 */
@Data
public class SchemaValidationRequestEntity {

    @JsonProperty("schemaType")
    private String schemaType;

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private Map<String, Object> data;
}
