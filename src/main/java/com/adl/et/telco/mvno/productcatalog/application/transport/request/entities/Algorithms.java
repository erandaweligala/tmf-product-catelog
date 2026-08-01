package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import lombok.Data;

import java.util.List;

@Data
public class Algorithms {
    private List<MatrixPrices> matrixPrice;
    private List<BasicInformations> basicInformation;
}
