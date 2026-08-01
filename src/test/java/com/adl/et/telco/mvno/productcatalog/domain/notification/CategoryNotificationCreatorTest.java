package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CategoryNotificationCreatorTest {

    @Mock
    private ObjectMapper mockMapper;

    private CategoryNotificationCreator notificationCreator;
    // private Object Collections;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationCreator = new CategoryNotificationCreator(mockMapper);
    }

    @Test
    void createNotifications_ShouldReturnSingleNotification() {
        // Prepare
        Category entity = new Category();

        // Mock
        when(mockMapper.convertValue(entity,Map.class)).thenReturn(Collections.emptyMap());

        // Execute
        List<Notification> notifications = notificationCreator.createNotifications(entity);

        // Verify
        assertEquals(1, notifications.size());
        Notification notification = notifications.get(0);
        assertEquals("CategoryCreateEvent", notification.getEventType());
        verify(mockMapper, times(1)).convertValue(entity,Map.class);
    }

    @Test
    void deleteNotifications_ShouldReturnSingleNotification() {
        // Prepare
        Category entity = new Category();

        // Mock
        when(mockMapper.convertValue(entity,Map.class)).thenReturn(Collections.emptyMap());

        // Execute
        List<Notification> notifications = notificationCreator.deleteNotifications(entity);

        // Verify
        assertEquals(1, notifications.size());
        Notification notification = notifications.get(0);
        assertEquals("CategoryDeleteEvent", notification.getEventType());
        verify(mockMapper, times(1)).convertValue(entity,Map.class);
    }



    @Test
    void updateNotifications_ShouldReturnEmptyList() {
        // Prepare
        Category original = new Category();
        Category merged = new Category();
        Category updated = new Category();

        // Execute
        List<Notification> notifications = notificationCreator.updateNotifications(
                Collections.singletonMap("", ""), original, merged, updated);

        // Verify
        assertEquals(0, notifications.size());
    }


    @Test
    void updateNotifications_WithPatchDef_ShouldReturnEmptyList() {
        // Prepare
        Category original = new Category();
        Category merged = new Category();
        Category updated = new Category();
        List<PatchDef> changeReq = Arrays.asList();

        // Execute
        List<Notification> notifications = notificationCreator.updateNotifications(
                changeReq, original, merged, updated);

        // Verify
        assertEquals(0, notifications.size());
    }
}