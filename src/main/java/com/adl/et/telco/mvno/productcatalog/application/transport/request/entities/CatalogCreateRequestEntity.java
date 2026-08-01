package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class CatalogCreateRequestEntity {

    @JsonProperty("catalogType")
    private String catalogType = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("category")
    @Valid
    private List<CategoryRefRequestEntity> category = null;

    @JsonProperty("relatedParty")
    @Valid
    private List<RelatedPartyRequestEntity> relatedParty = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

}
