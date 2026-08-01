package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.AbstractResourceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Catalog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CatalogNotificationCreator extends AbstractResourceNotificationCreator<Catalog> {

    protected CatalogNotificationCreator(ObjectMapper mapper) {

        super("catalog", "Catalog", mapper);
    }
}
