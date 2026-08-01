package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class ProductSpecificationCharacteristicValueUseRequestEntity {
    @JsonProperty("description")
    private String description = null;

    @JsonProperty("maxCardinality")
    private Integer maxCardinality = null;

    @JsonProperty("minCardinality")
    private Integer minCardinality = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("valueType")
    private String valueType = null;

    @JsonProperty("charType")
    private String charType = null;

    @JsonProperty("productSpecCharacteristicValue")
    @Valid
    private List<ProductSpecificationCharacteristicValueRequestEntity> productSpecCharacteristicValue = null;

    @JsonProperty("productSpecification")
    private ProductSpecificationRefRequestEntity productSpecification = null;

    @JsonProperty("validFor")
    private TimePeriodRequestEntity validFor = null;

    @JsonProperty("@baseType")
    private String baseType = null;

    @JsonProperty("@schemaLocation")
    private String schemaLocation = null;

    @JsonProperty("@type")
    private String type = null;
}
