package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Data
public class ProductOfferingPriceUpdateRequestEntity {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("isBundle")
    private Boolean isBundle = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("priceType")
    private String priceType = null;

    @JsonProperty("percentage")
    private Float percentage = null;

    @JsonProperty("recurringChargePeriodLength")
    private Integer recurringChargePeriodLength = null;

    @JsonProperty("recurringChargePeriodType")
    private String recurringChargePeriodType = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("bundledPopRelationship")
    @Valid
    private List<BundledProductOfferingPriceRelationshipRequestEntity> bundledPopRelationship = null;

    @JsonProperty("place")
    @Valid
    private List<PlaceRefRequestEntity> place = null;

    @JsonProperty("constraint")
    @Valid
    private List<ConstraintRefRequestEntity> constraint = null;

    @JsonProperty("popRelationship")
    @Valid
    private List<ProductOfferingPriceRelationshipRequestEntity> popRelationship = null;

    @JsonProperty("price")
    private MoneyRequestEntity price = null;

    @JsonProperty("pricingLogicAlgorithm")
    @Valid
    private List<Map<String, Object>> pricingLogicAlgorithm = null;

    @JsonProperty("prodSpecCharValueUse")
    @Valid
    private List<ProductSpecificationCharacteristicValueUseRequestEntity> prodSpecCharValueUse = null;

    @JsonProperty("tax")
    @Valid
    private List<TaxItemRequestEntity> tax = null;

    @JsonProperty("productOfferingTerm")
    @Valid
    private List<ProductOfferingTermRequestEntity> productOfferingTerm = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("unitOfMeasure")
    private QuantityRequestEntity unitOfMeasure = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}

