package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatrixPrices {
    private Boolean isVisible;
    private String name;
    private String code;
    private List<String> rowDatas;
    private List<BasicInformations> basicInformation;
    private List<HeadDefinitions> headDefinition;
}
