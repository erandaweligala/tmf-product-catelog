package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
public class SchemaUpdateRequestEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("schema")
    private Map<String, Object> schema;

    @JsonProperty("schemaType")
    private String schemaType;

    @JsonProperty("lastUpdate")
    @Valid
    private OffsetDateTime lastUpdate = null;

    @JsonProperty("version")
    private String version = "1.0";

    @JsonProperty("validFor")
    @Valid
    private TimePeriodRequestEntity validFor = null;
}
