package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ProductOfferingUpdateRequestEntity {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("isBundle")
    private Boolean isBundle = null;

    @JsonProperty("productNumber")
    private String productNumber = null;

    @JsonProperty("isSellable")
    private Boolean isSellable = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("statusReason")
    private String statusReason = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("agreement")
    @Valid
    private List<AgreementRefRequestEntity> agreement = null;

    @JsonProperty("bundledProductOffering")
    @Valid
    private List<BundledProductOfferingRequestEntity> bundledProductOffering = null;

    @JsonProperty("attachment")
    @Valid
    private List<AttachmentRefOrValueRequestEntity> attachment = null;

    @JsonProperty("category")
    @Valid
    private List<CategoryRefRequestEntity> category = null;

    @JsonProperty("channel")
    @Valid
    private List<ChannelRefRequestEntity> channel = null;

    @JsonProperty("marketSegment")
    @Valid
    private List<MarketSegmentRefRequestEntity> marketSegment = null;

    @JsonProperty("prodSpecCharValueUse")
    @Valid
    private List<ProductSpecificationCharacteristicValueUseRequestEntity> prodSpecCharValueUse = null;

    @JsonProperty("productOfferingTerm")
    @Valid
    private List<ProductOfferingTermRequestEntity> productOfferingTerm = null;

    @JsonProperty("place")
    @Valid
    private List<PlaceRefRequestEntity> place = null;

    @JsonProperty("productOfferingPrice")
    @Valid
    private List<ProductOfferingPriceRefRequestEntity> productOfferingPrice = null;

    @JsonProperty("productSpecification")
    private ProductSpecificationRefRequestEntity productSpecification = null;

    @JsonProperty("resourceCandidate")
    private ResourceCandidateRefRequestEntity resourceCandidate = null;

    @JsonProperty("serviceCandidate")
    private ServiceCandidateRefRequestEntity serviceCandidate = null;

    @JsonProperty("serviceLevelAgreement")
    private SLARefRequestEntity serviceLevelAgreement = null;

    @JsonProperty("productOfferingRelationship")
    private List<ProductOfferingRelationshipRequestEntity> productOfferingRelationship = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    public ProductOfferingUpdateRequestEntity description(String description) {
        this.description = description;
        return this;
    }
}
