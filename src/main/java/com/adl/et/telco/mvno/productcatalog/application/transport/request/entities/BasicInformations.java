package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicInformations {
    private Boolean isVisible;
    private String name;
    private String type;
    private String code;
    private List<Fileld> fields;
}
