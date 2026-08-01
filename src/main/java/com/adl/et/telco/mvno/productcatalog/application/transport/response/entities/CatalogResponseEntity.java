package com.adl.et.telco.mvno.productcatalog.application.transport.response.entities;

import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.CategoryRefRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.RelatedPartyRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.TimePeriodRequestEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class CatalogResponseEntity {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("href")
    private String href = null;

    @JsonProperty("catalogType")
    private String catalogType = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("relatedParty")
    @Valid
    private List<RelatedPartyRequestEntity> relatedParty = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("category")
    @Valid
    private List<CategoryRefRequestEntity> category = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}

