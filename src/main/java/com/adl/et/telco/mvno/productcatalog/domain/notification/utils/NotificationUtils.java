package com.adl.et.telco.mvno.productcatalog.domain.notification.utils;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationUtils {

    public static Notification createNotification(String eventType, String eventPayloadName, Object entity) {

        Notification notification = new Notification();

        notification.setEventId(generateEventId());
        notification.setEventTime(OffsetDateTime.now());
        notification.setEventType(eventType);

        Map<String, Object> eventPayload = new HashMap<>();
        eventPayload.put(eventPayloadName, entity);
        notification.setEvent(eventPayload);

        return notification;
    }

    private static String generateEventId() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String formattedDate = myDateObj.format(myFormatObj);
        return (new Random().nextInt((999999 - 100000) + 1) + 100000) + formattedDate;
    }
}
