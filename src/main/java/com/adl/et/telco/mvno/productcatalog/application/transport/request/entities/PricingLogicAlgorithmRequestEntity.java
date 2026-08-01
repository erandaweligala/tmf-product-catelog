package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.adl.et.telco.mvno.productcatalog.domain.entities.ValueDefinitions;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Values;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingLogicAlgorithmRequestEntity {

    private String id;
    private String priceType;
    private String algoType;
    private String cbsTemplate;
    private String href;
    @JsonProperty("@type")
    private String type;
    @JsonProperty("@baseType")
    private String baseType;
    @JsonProperty("@schemaLocation")
    private String schemaLocation;
    private String plaSpecId;
    private String name;
    private String description;
    private TimePeriodRequestEntity validFor;
    private ValueDefinitions valueDefinitions;
    private Algorithms algorithm;
    private Values values;
}
