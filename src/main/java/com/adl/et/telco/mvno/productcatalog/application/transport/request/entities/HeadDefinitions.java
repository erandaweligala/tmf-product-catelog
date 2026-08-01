package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadDefinitions {
    private String index;
    @JsonProperty("isVisible")
    private boolean isVisible;
    @JsonProperty("isRequired")
    private boolean isRequired;
    @JsonProperty("isEditable")
    private boolean isEditable;
    private String dataType;
    private String columnName;
    private String fieldCode;
    private String defaultValue;
    private String operator;
    private String value;
    private List<EnumValues> enumValues;
}
