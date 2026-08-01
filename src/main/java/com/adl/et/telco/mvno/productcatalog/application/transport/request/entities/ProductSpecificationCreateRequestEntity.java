package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ProductSpecificationCreateRequestEntity {

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isBundle")
    private Boolean isBundle;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus;

    @JsonProperty("name")
    @NotEmpty
    private String name;

    @JsonProperty("productNumber")
    private String productNumber;

    @JsonProperty("version")
    private String version = "1.0";

    @JsonProperty("attachment")
    @Valid
    private List<AttachmentRefOrValueRequestEntity> attachment;

    @JsonProperty("bundledProductSpecification")
    @Valid
    private List<BundledProductSpecificationRequestEntity> bundledProductSpecification;

    @JsonProperty("productSpecCharacteristic")
    @Valid
    private List<ProductSpecificationCharacteristicRequestEntity> productSpecCharacteristic;

    @JsonProperty("productSpecificationRelationship")
    @Valid
    private List<ProductSpecificationRelationshipRequestEntity> productSpecificationRelationship;

    @JsonProperty("relatedParty")
    @Valid
    private List<RelatedPartyRequestEntity> relatedParty;

    @JsonProperty("resourceSpecification")
    @Valid
    private List<ResourceSpecificationRefRequestEntity> resourceSpecification;

    @JsonProperty("serviceSpecification")
    @Valid
    private List<ServiceSpecificationRefRequestEntity> serviceSpecification;

    @JsonProperty("targetProductSchema")
    private TargetProductSchemaRequestEntity targetProductSchema;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}
