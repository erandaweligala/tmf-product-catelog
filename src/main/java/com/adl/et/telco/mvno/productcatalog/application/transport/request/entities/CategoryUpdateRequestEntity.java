package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryUpdateRequestEntity {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("isRoot")
    private Boolean isRoot = null;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("parentId")
    private String parentId = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("productOffering")
    private List<ProductOfferingRefRequestEntity> productOffering = null;

    @JsonProperty("subCategory")
    private List<CategoryRefRequestEntity> subCategory = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}
