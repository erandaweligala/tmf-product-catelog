package com.adl.et.telco.mvno.productcatalog.application.transport.response.entities;

import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.TimePeriodRequestEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
public class SchemaResponseEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate;

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String version;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("schemaType")
    private String schemaType;

    @JsonProperty("schema")
    private Map<String, Object> schema;
}
