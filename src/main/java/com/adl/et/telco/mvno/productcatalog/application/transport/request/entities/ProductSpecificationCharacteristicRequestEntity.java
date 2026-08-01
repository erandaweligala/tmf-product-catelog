package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ProductSpecificationCharacteristicRequestEntity {

    @JsonProperty("configurable")
    private Boolean configurable;

    @JsonProperty("description")
    private String description;

    @JsonProperty("extensible")
    private Boolean extensible;

    @JsonProperty("isUnique")
    private Boolean isUnique;

    @JsonProperty("maxCardinality")
    private Integer maxCardinality;

    @JsonProperty("minCardinality")
    private Integer minCardinality;

    @JsonProperty("name")
    private String name;

    @JsonProperty("regex")
    private String regex;

    @JsonProperty("valueType")
    private String valueType;

    @JsonProperty("charType")
    private String charType;

    @JsonProperty("productSpecCharRelationship")
    @Valid
    private List<ProductSpecificationCharacteristicRelationshipRequestEntity> productSpecCharRelationship;

    @JsonProperty("productSpecCharacteristicValue")
    @Valid
    private List<ProductSpecificationCharacteristicValueRequestEntity> productSpecCharacteristicValue;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@valueSchemaLocation")
    private String valueSchemaLocation;
}
