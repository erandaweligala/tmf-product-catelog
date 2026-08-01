package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductOfferingRelationshipRequestEntity {
    private String id;
    private String href;
    private String name;
    private String relationShipType;
    private String role;
    private TimePeriodRequestEntity validFor;
    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@referredType")
    private String referredType;
}
