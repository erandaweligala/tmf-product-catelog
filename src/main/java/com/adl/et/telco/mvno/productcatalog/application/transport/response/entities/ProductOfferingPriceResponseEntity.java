package com.adl.et.telco.mvno.productcatalog.application.transport.response.entities;

import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ProductOfferingPriceResponseEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isBundle")
    private Boolean isBundle;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus;

    @JsonProperty("name")
    private String name;

    @JsonProperty("percentage")
    private Float percentage;

    @JsonProperty("priceType")
    private String priceType;

    @JsonProperty("recurringChargePeriodLength")
    private Integer recurringChargePeriodLength;

    @JsonProperty("recurringChargePeriodType")
    private String recurringChargePeriodType;

    @JsonProperty("version")
    private String version;

    @JsonProperty("bundledPopRelationship")
    private List<BundledProductOfferingPriceRelationshipRequestEntity> bundledPopRelationship;

    @JsonProperty("constraint")
    private List<ConstraintRefRequestEntity> constraint;

    @JsonProperty("place")
    private List<PlaceRefRequestEntity> place;

    @JsonProperty("popRelationship")
    private List<ProductOfferingPriceRelationshipRequestEntity> popRelationship;

    @JsonProperty("price")
    private MoneyRequestEntity price;

    @JsonProperty("pricingLogicAlgorithm")
    private List<Map<String, Object>> pricingLogicAlgorithm;

    @JsonProperty("prodSpecCharValueUse")
    private List<ProductSpecificationCharacteristicValueUseRequestEntity> prodSpecCharValueUse;

    @JsonProperty("productOfferingTerm")
    private List<ProductOfferingTermRequestEntity> productOfferingTerm;

    @JsonProperty("tax")
    private List<TaxItemRequestEntity> tax;

    @JsonProperty("unitOfMeasure")
    private QuantityRequestEntity unitOfMeasure;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}

