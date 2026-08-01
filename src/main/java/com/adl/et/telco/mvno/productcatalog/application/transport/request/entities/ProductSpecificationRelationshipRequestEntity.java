package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductSpecificationRelationshipRequestEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("relationshipType")
    private String relationshipType;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}
