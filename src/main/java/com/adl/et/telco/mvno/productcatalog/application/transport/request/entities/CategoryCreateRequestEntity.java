package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class CategoryCreateRequestEntity {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("isRoot")
    private Boolean isRoot = true;

    @JsonProperty("lastUpdate")
    @Valid
    private OffsetDateTime lastUpdate = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    @NotNull
    @NotEmpty
    private String name = null;

    @JsonProperty("parentId")
    private String parentId = null;

    @JsonProperty("version")
    private String version = "1.0";

    @JsonProperty("productOffering")
    @Valid
    private List<ProductOfferingRefRequestEntity> productOffering = null;

    @JsonProperty("subCategory")
    @Valid
    private List<CategoryRefRequestEntity> subCategory = null;

    @JsonProperty("validFor")
    @Valid
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@type")
    private String type = null;
}
