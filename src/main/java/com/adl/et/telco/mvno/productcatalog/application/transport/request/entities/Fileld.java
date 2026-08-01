package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fileld {
    @JsonProperty("isEditable")
    private boolean isEditable;
    @JsonProperty("isVisible")
    private boolean isVisible;
    @JsonProperty("isRequired")
    private boolean isRequired;
    private String dataType;
    private String fieldName;
    private String fieldDisplayName;
    private String columnName;
    private String fieldCode;
    private String defaultValue;
    private String value;
    private List<EnumValues> enumValues;
}
