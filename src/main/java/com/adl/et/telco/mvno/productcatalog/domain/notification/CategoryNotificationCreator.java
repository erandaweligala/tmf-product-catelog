package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.AbstractResourceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryNotificationCreator extends AbstractResourceNotificationCreator<Category> {

    protected CategoryNotificationCreator(ObjectMapper mapper) {

        super("category", "Category", mapper);
    }
}
