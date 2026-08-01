
package com.adl.et.telco.mvno.productcatalog.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "#{@productOfferingService.getCatalogCollectionName()}productOffering")
public class ProductOffering extends CatalogResourceDocument {

    private String description;

    private Boolean isBundle;

    private String productNumber;

    private Boolean isSellable;

    private String lifecycleStatus;

    private String name;

    private String statusReason;

    private List<AgreementRef> agreement;

    private List<AttachmentRefOrValue> attachment;

    private List<BundledProductOffering> bundledProductOffering;

    private List<CategoryRef> category;

    private List<ChannelRef> channel;

    private List<MarketSegmentRef> marketSegment;

    private List<PlaceRef> place;

    private List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse;

    private List<ProductOfferingPriceRef> productOfferingPrice;

    private List<ProductOfferingTerm> productOfferingTerm;

    private ProductSpecificationRef productSpecification;

    private ResourceCandidateRef resourceCandidate;

    private ServiceCandidateRef serviceCandidate;

    private SLARef serviceLevelAgreement;

    private List<ProductOfferingRelationship> productOfferingRelationship;

    @JsonProperty("@baseType")
    @Field("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    @Field("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    @Field("@type")
    private String type;


}