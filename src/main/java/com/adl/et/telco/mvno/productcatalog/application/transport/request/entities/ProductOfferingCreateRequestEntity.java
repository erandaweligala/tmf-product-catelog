package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ProductOfferingCreateRequestEntity {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("isBundle")
    private Boolean isBundle = false;

    @JsonProperty("productNumber")
    private String productNumber = null;

    @JsonProperty("isSellable")
    private Boolean isSellable = true;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("statusReason")
    private String statusReason = null;

    @JsonProperty("version")
    private String version = "1.0";

    @JsonProperty("agreement")
    @Valid
    private List<AgreementRefRequestEntity> agreement = null;

    @JsonProperty("attachment")
    @Valid
    private List<AttachmentRefOrValueRequestEntity> attachment = null;

    @JsonProperty("bundledProductOffering")
    @Valid
    private List<BundledProductOfferingRequestEntity> bundledProductOffering = null;

    @JsonProperty("channel")
    @Valid
    private List<ChannelRefRequestEntity> channel = null;

    @JsonProperty("category")
    @Valid
    private List<CategoryRefRequestEntity> category = null;


    @JsonProperty("marketSegment")
    @Valid
    private List<MarketSegmentRefRequestEntity> marketSegment = null;

    @JsonProperty("place")
    @Valid
    private List<PlaceRefRequestEntity> place = null;

    @JsonProperty("prodSpecCharValueUse")
    @Valid
    private List<ProductSpecificationCharacteristicValueUseRequestEntity> prodSpecCharValueUse = null;

    @JsonProperty("productOfferingPrice")
    @Valid
    private List<ProductOfferingPriceRefRequestEntity> productOfferingPrice = null;

    @JsonProperty("productOfferingTerm")
    @Valid
    private List<ProductOfferingTermRequestEntity> productOfferingTerm = null;

    @JsonProperty("productSpecification")
    private ProductSpecificationRefRequestEntity productSpecification = null;

    @JsonProperty("serviceCandidate")
    private ServiceCandidateRefRequestEntity serviceCandidate = null;

    @JsonProperty("resourceCandidate")
    private ResourceCandidateRefRequestEntity resourceCandidate = null;

    @JsonProperty("productOfferingRelationship")
    private List<ProductOfferingRelationshipRequestEntity> productOfferingRelationship = null;
    @JsonProperty("serviceLevelAgreement")
    private SLARefRequestEntity serviceLevelAgreement = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}

