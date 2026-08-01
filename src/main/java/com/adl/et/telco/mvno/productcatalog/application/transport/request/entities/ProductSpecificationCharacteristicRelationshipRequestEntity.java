package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductSpecificationCharacteristicRelationshipRequestEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("charSpecSeq")
    private Integer charSpecSeq;

    @JsonProperty("name")
    private String name;

    @JsonProperty("relationshipType")
    private String relationshipType;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

}
