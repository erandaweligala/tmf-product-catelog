package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class CatalogUpdateRequestEntity {

    @JsonProperty("catalogType")
    private String catalogType = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("relatedParty")
    @Valid
    private List<RelatedPartyRequestEntity> relatedParty = null;

    @JsonProperty("category")
    @Valid
    private List<CategoryRefRequestEntity> category = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@type")
    private String type = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

}
