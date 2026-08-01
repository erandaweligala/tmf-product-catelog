package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuantityRequestEntity {

    @JsonProperty("amount")
    private Float amount = 1.0f;

    @JsonProperty("units")
    private String units;
}
