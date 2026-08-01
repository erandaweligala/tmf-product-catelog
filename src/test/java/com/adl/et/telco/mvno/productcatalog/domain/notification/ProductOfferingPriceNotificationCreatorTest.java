package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductOfferingPriceNotificationCreatorTest {
    private ProductOfferingPriceNotificationCreator creator;
    private ProductOfferingPrice original;
    private ProductOfferingPrice merged;
    private ProductOfferingPrice updated;

    @BeforeEach
    public void setUp() {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        creator = new ProductOfferingPriceNotificationCreator(mapper);


    }


    @Test
    public void updateNotifications_Map_AttributeValueChanged_ShouldReturnNotifications() {

        original = new ProductOfferingPrice();
        original.setName("Name1");
        merged = new ProductOfferingPrice();
        merged.setName("Name2");
        updated = new ProductOfferingPrice();
        updated.setName("Name3");

        List<Notification> notifications = creator.updateNotifications(original, merged, updated);
        assertEquals(1, notifications.size());
        assertEquals("ProductOfferingPriceAttributeValueChangeEvent", notifications.get(0).getEventType());
    }

}