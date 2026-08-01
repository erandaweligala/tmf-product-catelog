package com.adl.et.telco.mvno.productcatalog.application.transport.response.entities;

import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ProductSpecificationResponseEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("description")
    private String description;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate;

    @JsonProperty("isBundle")
    private Boolean isBundle;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus;

    @JsonProperty("productNumber")
    private String productNumber;

    @JsonProperty("version")
    private String version;

    @JsonProperty("attachment")
    private List<AttachmentRefOrValueRequestEntity> attachment;

    @JsonProperty("bundledProductSpecification")
    private List<BundledProductSpecificationRequestEntity> bundledProductSpecification;

    @JsonProperty("productSpecCharacteristic")
    private List<ProductSpecificationCharacteristicRequestEntity> productSpecCharacteristic;

    @JsonProperty("productSpecificationRelationship")
    private List<ProductSpecificationRelationshipRequestEntity> productSpecificationRelationship;

    @JsonProperty("relatedParty")
    private List<RelatedPartyRequestEntity> relatedParty;

    @JsonProperty("resourceSpecification")
    private List<ResourceSpecificationRefRequestEntity> resourceSpecification;

    @JsonProperty("serviceSpecification")
    private List<ServiceSpecificationRefRequestEntity> serviceSpecification;

    @JsonProperty("targetProductSchema")
    private TargetProductSchemaRequestEntity targetProductSchema;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}
