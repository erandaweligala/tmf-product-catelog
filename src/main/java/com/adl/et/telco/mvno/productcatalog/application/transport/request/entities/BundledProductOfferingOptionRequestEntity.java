package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BundledProductOfferingOptionRequestEntity {

    @JsonProperty("numberRelOfferDefault")
    private Integer numberRelOfferDefault = null;

    @JsonProperty("numberRelOfferLowerLimit")
    private Integer numberRelOfferLowerLimit = null;

    @JsonProperty("numberRelOfferUpperLimit")
    private Integer numberRelOfferUpperLimit = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}
