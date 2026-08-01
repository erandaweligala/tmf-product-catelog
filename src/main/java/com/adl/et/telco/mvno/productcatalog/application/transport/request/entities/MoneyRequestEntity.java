package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MoneyRequestEntity {

    @JsonProperty("unit")
    private String unit = null;

    @JsonProperty("value")
    private Float value = null;
}

