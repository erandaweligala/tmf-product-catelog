package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ProductSpecificationUpdateRequestEntity {

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isBundle")
    private Boolean isBundle;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus;

    @JsonProperty("name")
    private String name;

    @JsonProperty("productNumber")
    private String productNumber;

    @JsonProperty("version")
    private String version;

    @JsonProperty("attachment")
    @Valid
    private List<TimePeriodRequestEntity> attachment;

    @JsonProperty("bundledProductSpecification")
    @Valid
    private List<TimePeriodRequestEntity> bundledProductSpecification;

    @JsonProperty("productSpecCharacteristic")
    @Valid
    private List<TimePeriodRequestEntity> productSpecCharacteristic;

    @JsonProperty("productSpecificationRelationship")
    @Valid
    private List<TimePeriodRequestEntity> productSpecificationRelationship;

    @JsonProperty("relatedParty")
    @Valid
    private List<TimePeriodRequestEntity> relatedParty;

    @JsonProperty("resourceSpecification")
    @Valid
    private List<TimePeriodRequestEntity> resourceSpecification;

    @JsonProperty("serviceSpecification")
    @Valid
    private List<TimePeriodRequestEntity> serviceSpecification;

    @JsonProperty("targetProductSchema")
    private TimePeriodRequestEntity targetProductSchema;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}
