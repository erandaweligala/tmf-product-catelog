package com.adl.et.telco.mvno.productcatalog.application.transport.response.entities;

import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.CategoryRefRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.ProductOfferingRefRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.TimePeriodRequestEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class CategoryResponseEntity {

    @JsonProperty("id")
    private String id;

    @JsonProperty("href")
    private String href;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isRoot")
    private Boolean isRoot;

    @JsonProperty("lastUpdate")
    private OffsetDateTime lastUpdate;

    @JsonProperty("lifecycleStatus")
    private String lifecycleStatus;

    @JsonProperty("name")
    private String name;

    @JsonProperty("parentId")
    private String parentId;

    @JsonProperty("version")
    private String version;

    @JsonProperty("productOffering")
    private List<ProductOfferingRefRequestEntity> productOffering;

    @JsonProperty("subCategory")
    private List<CategoryRefRequestEntity> subCategory;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor;

    @JsonProperty("@baseType")
    private String baseType;

    @JsonProperty("@schemaLocation")
    private String schemaLocation;

    @JsonProperty("@type")
    private String type;
}
