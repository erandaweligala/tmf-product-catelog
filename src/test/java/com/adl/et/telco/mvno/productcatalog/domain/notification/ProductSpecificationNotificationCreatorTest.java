package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductSpecificationNotificationCreatorTest {

    @Mock
    private ObjectMapper mockMapper;

    private ProductSpecificationNotificationCreator notificationCreator;
   // private Object Collections;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationCreator = new ProductSpecificationNotificationCreator(mockMapper);
    }

    @Test
    void createNotifications_ShouldReturnSingleNotification() {
        // Prepare
        ProductSpecification entity = new ProductSpecification();

        // Mock
        when(mockMapper.convertValue(entity,Map.class)).thenReturn(Collections.emptyMap());

        // Execute
        List<Notification> notifications = notificationCreator.createNotifications(entity);

        // Verify
        assertEquals(1, notifications.size());
        Notification notification = notifications.get(0);
        assertEquals("ProductSpecificationCreateEvent", notification.getEventType());
        verify(mockMapper, times(1)).convertValue(entity,Map.class);
    }

    @Test
    void deleteNotifications_ShouldReturnSingleNotification() {
        // Prepare
        ProductSpecification entity = new ProductSpecification();

        // Mock
        when(mockMapper.convertValue(entity,Map.class)).thenReturn(Collections.emptyMap());

        // Execute
        List<Notification> notifications = notificationCreator.deleteNotifications(entity);

        // Verify
        assertEquals(1, notifications.size());
        Notification notification = notifications.get(0);
        assertEquals("ProductSpecificationDeleteEvent", notification.getEventType());
        verify(mockMapper, times(1)).convertValue(entity,Map.class);
    }


    @Test
    void updateNotifications_ShouldReturnEmptyList() {
        // Prepare
        ProductSpecification original = new ProductSpecification();
        ProductSpecification merged = new ProductSpecification();
        ProductSpecification updated = new ProductSpecification();

        // Execute
        List<Notification> notifications = notificationCreator.updateNotifications(
                Collections.singletonMap("", ""), original, merged, updated);

        // Verify
        assertEquals(0, notifications.size());
    }


    @Test
    void updateNotifications_WithPatchDef_ShouldReturnEmptyList() {
        // Prepare
        ProductSpecification original = new ProductSpecification();
        ProductSpecification merged = new ProductSpecification();
        ProductSpecification updated = new ProductSpecification();
        List<PatchDef> changeReq = Arrays.asList();

        // Execute
        List<Notification> notifications = notificationCreator.updateNotifications(
                changeReq, original, merged, updated);

        // Verify
        assertEquals(0, notifications.size());
    }
}

