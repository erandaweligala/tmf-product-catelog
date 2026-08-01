package com.adl.et.telco.mvno.productcatalog.application.transport.request.entities;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SubscriberRequestEntity {

    @NotEmpty
    private String callback;
    private String query;
}
