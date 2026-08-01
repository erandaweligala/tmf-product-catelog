package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TimePeriodRequestEntity {

    @JsonProperty("endDateTime")
    private OffsetDateTime endDateTime = null;

    @JsonProperty("startDateTime")
    private OffsetDateTime startDateTime = null;
}
