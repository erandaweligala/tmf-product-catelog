package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductOfferingNotificationCreatorTest {

    private ProductOfferingNotificationCreator creator;
    private ProductOffering original;
    private ProductOffering merged;
    private ProductOffering updated;

    Map<String, Object> changeReq = new HashMap<>();

    @BeforeEach
    public void setUp() {
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        creator = new ProductOfferingNotificationCreator(mapper);

    }

    @Test
    public void updateNotifications_Map_NoChangeInAttributes_ShouldReturnEmptyList() {

        List<Notification> result = creator.updateNotifications(changeReq, original, merged, updated);

        assertEquals(true, result.isEmpty());


    }





    @Test
    public void updateNotifications_Map_AttributeValueChanged_ShouldReturnNotifications() {
        Map<String, Object> changeReq = Collections.singletonMap("key", "value");
        original = new ProductOffering();
        original.setName("Name1");
        merged = new ProductOffering();
        merged.setName("Name2");
        updated = new ProductOffering();
        updated.setName("Name3");

        List<Notification> notifications = creator.updateNotifications(changeReq, original, merged, updated);
        assertEquals(1, notifications.size());
        assertEquals("ProductOfferingAttributeValueChangeEvent", notifications.get(0).getEventType());
    }

    @Test
    public void updateNotifications_Map_LifecycleStatusChanged_ShouldReturnNotifications() {
        Map<String, Object> changeReq = new HashMap<>();
        changeReq.put("lifecycleStatus", "ACTIVE");
        original = new ProductOffering();
        original.setLifecycleStatus("NotACTIVE");



        List<Notification> notifications2 = creator.updateNotifications(changeReq, original, merged, updated);
        assertEquals(2, notifications2.size());
        assertEquals("ProductOfferingStateChangeEvent", notifications2.get(1).getEventType());
    }


    @Test
    public void updateNotifications_PatchDef_AttributeValueChanged_ShouldReturnNotifications() {

        Map<String, Object> changeReq = new HashMap<>();

        changeReq.put("lifecycleStatus", "ACTIVE");

        original = new ProductOffering();
        original.setLifecycleStatus("ACTIVE");
        merged = new ProductOffering();
        merged.setLifecycleStatus("ACTIVE");


        List<Notification> result = creator.updateNotifications(changeReq, original, merged, updated);
        assertEquals(0,result.size());




    }


}
