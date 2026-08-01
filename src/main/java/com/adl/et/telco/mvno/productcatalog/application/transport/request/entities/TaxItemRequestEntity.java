package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaxItemRequestEntity {

    @JsonProperty("taxCategory")
    private String taxCategory = null;

    @JsonProperty("taxRate")
    private Float taxRate = null;

    @JsonProperty("taxAmount")
    private MoneyRequestEntity taxAmount = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}
