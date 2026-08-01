package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.AbstractResourceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecificationNotificationCreator extends AbstractResourceNotificationCreator<ProductSpecification> {

    protected ProductSpecificationNotificationCreator(ObjectMapper mapper) {

        super("productSpecification", "ProductSpecification", mapper);
    }
}
