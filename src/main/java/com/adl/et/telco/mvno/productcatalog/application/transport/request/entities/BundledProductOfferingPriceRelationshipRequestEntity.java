package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BundledProductOfferingPriceRelationshipRequestEntity {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;
}

