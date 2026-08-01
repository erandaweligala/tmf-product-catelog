package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ProductOfferingPriceCreateRequestEntity {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("isBundle")
    private Boolean isBundle = null;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("percentage")
    private Float percentage = null;

    @JsonProperty("priceType")
    private String priceType = null;

    @JsonProperty("recurringChargePeriodLength")
    private Integer recurringChargePeriodLength = null;

    @JsonProperty("recurringChargePeriodType")
    private String recurringChargePeriodType = null;

    @JsonProperty("version")
    private String version = "1.0";

    @JsonProperty("bundledPopRelationship")
    @Valid
    private List<BundledProductOfferingPriceRelationshipRequestEntity> bundledPopRelationship = null;

    @JsonProperty("constraint")
    @Valid
    private List<ConstraintRefRequestEntity> constraint = null;

    @JsonProperty("place")
    @Valid
    private List<PlaceRefRequestEntity> place = null;

    @JsonProperty("popRelationship")
    @Valid
    private List<ProductOfferingPriceRelationshipRequestEntity> popRelationship = null;

    @JsonProperty("pricingLogicAlgorithm")
    @Valid
    private List<Map<String, Object>> pricingLogicAlgorithm = null;

    @JsonProperty("price")
    private MoneyRequestEntity price = null;


    @JsonProperty("prodSpecCharValueUse")
    @Valid
    private List<ProductSpecificationCharacteristicValueUseRequestEntity> prodSpecCharValueUse = null;

    @JsonProperty("productOfferingTerm")
    @Valid
    private List<ProductOfferingTermRequestEntity> productOfferingTerm = null;

    @JsonProperty("tax")
    @Valid
    private List<TaxItemRequestEntity> tax = null;

    @JsonProperty("unitOfMeasure")
    private QuantityRequestEntity unitOfMeasure = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}

