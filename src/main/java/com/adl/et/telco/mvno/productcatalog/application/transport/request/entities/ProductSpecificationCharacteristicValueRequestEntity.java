package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductSpecificationCharacteristicValueRequestEntity {

    @JsonProperty("isDefault")
    private Boolean isDefault;

    @JsonProperty("rangeInterval")
    private String rangeInterval;

    @JsonProperty("regex")
    private String regex;

    @JsonProperty("unitOfMeasure")
    private String unitOfMeasure;

    @JsonProperty("valueFrom")
    private String valueFrom;

    @JsonProperty("valueTo")
    private String valueTo;

    @JsonProperty("valueType")
    private String valueType = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("value")
    private Object value;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}
