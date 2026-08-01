package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
public class SchemaCreateRequestEntity {

    @JsonProperty("name")
    @NotEmpty
    private String name;

    @JsonProperty("schemaType")
    @NotEmpty
    private String schemaType;

    @JsonProperty("schema")
    private Map<String, Object> schema;

    @JsonProperty("lastUpdate")
    @Valid
    private OffsetDateTime lastUpdate = null;

    @JsonProperty("version")
    private String version = "1.0";

    @JsonProperty("validFor")
    @Valid
    private TimePeriodRequestEntity validFor = null;


}
