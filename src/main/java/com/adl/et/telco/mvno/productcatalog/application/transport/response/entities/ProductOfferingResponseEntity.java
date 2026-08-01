package com.adl.et.telco.mvno.productcatalog.application.transport.response.entities;

import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.*;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingRelationship;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ProductOfferingResponseEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isBundle")
    private Boolean isBundle;

    @JsonProperty("productNumber")
    private String productNumber;

    @JsonProperty("isSellable")
    private Boolean isSellable;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus;

    @JsonProperty("name")
    private String name;

    @JsonProperty("statusReason")
    private String statusReason;

    @JsonProperty("version")
    private String version;

    @JsonProperty("agreement")
    private List<AgreementRefRequestEntity> agreement;

    @JsonProperty("attachment")
    private List<AttachmentRefOrValueRequestEntity> attachment;

    @JsonProperty("bundledProductOffering")
    private List<BundledProductOfferingRequestEntity> bundledProductOffering;

    @JsonProperty("category")
    private List<CategoryRefRequestEntity> category;

    @JsonProperty("channel")
    private List<ChannelRefRequestEntity> channel;

    @JsonProperty("marketSegment")
    private List<MarketSegmentRefRequestEntity> marketSegment;

    @JsonProperty("place")
    private List<PlaceRefRequestEntity> place;

    @JsonProperty("prodSpecCharValueUse")
    private List<ProductSpecificationCharacteristicValueUseRequestEntity> prodSpecCharValueUse;

    @JsonProperty("productOfferingPrice")
    private List<ProductOfferingPriceRefRequestEntity> productOfferingPrice;

    @JsonProperty("productOfferingTerm")
    private List<ProductOfferingTermRequestEntity> productOfferingTerm;

    @JsonProperty("productSpecification")
    private ProductSpecificationRefRequestEntity productSpecification;

    @JsonProperty("resourceCandidate")
    private ResourceCandidateRefRequestEntity resourceCandidate;

    @JsonProperty("serviceCandidate")
    private ServiceCandidateRefRequestEntity serviceCandidate;

    @JsonProperty("serviceLevelAgreement")
    private SLARefRequestEntity serviceLevelAgreement;

    private List<ProductOfferingRelationship> productOfferingRelationship;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}
