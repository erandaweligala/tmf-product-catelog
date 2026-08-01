package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class SchemaNotificationCreator implements ResourceNotificationCreator<SchemaEntity> {

    @Override
    public List<Notification> createNotifications(SchemaEntity entity) {
        return Collections.emptyList();
    }

    @Override
    public List<Notification> updateNotifications(Map<String, Object> changeReq, SchemaEntity original, SchemaEntity merged, SchemaEntity updated) {
        return Collections.emptyList();
    }

    @Override
    public List<Notification> updateNotifications(List<PatchDef> changeReq, SchemaEntity original, SchemaEntity merged, SchemaEntity updated) {
        return Collections.emptyList();
    }

    @Override
    public List<Notification> deleteNotifications(SchemaEntity entity) {
        return Collections.emptyList();
    }
}
