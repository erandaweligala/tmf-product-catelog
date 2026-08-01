package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PricingLogicAlgorithmNotificationCreatorTest {

    private PricingLogicAlgorithmNotificationCreator creator;
    private PricingLogicAlgorithm pricingLogicAlgorithm;

    @BeforeEach
    void setUp() {
        creator = new PricingLogicAlgorithmNotificationCreator();
        pricingLogicAlgorithm = new PricingLogicAlgorithm();
        // Set up the pricingLogicAlgorithm object if needed for specific test cases
    }

    @Test
    void createNotifications_ShouldReturnEmptyList() {
        List<Notification> notifications = creator.createNotifications(pricingLogicAlgorithm);
        assertTrue(notifications.isEmpty(), "Expected an empty list of notifications");
    }

    @Test
    void updateNotifications_Map_ShouldReturnEmptyList() {
        Map<String, Object> changeReq = Collections.singletonMap("key", "value");
        List<Notification> notifications = creator.updateNotifications(changeReq, pricingLogicAlgorithm, null, null);
        assertTrue(notifications.isEmpty(), "Expected an empty list of notifications");
    }

    @Test
    void updateNotifications_List_ShouldReturnEmptyList() {
        List<PatchDef> changeReq = Collections.singletonList(new PatchDef());
        List<Notification> notifications = creator.updateNotifications(changeReq, pricingLogicAlgorithm, null, null);
        assertTrue(notifications.isEmpty(), "Expected an empty list of notifications");
    }

    @Test
     void deleteNotifications_ShouldReturnEmptyList() {
        List<Notification> notifications = creator.deleteNotifications(pricingLogicAlgorithm);
        assertTrue(notifications.isEmpty(), "Expected an empty list of notifications");
    }
}
