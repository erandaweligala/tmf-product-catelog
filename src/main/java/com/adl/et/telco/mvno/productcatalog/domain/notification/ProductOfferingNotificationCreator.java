package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.AbstractResourceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.adl.et.telco.mvno.productcatalog.domain.notification.utils.NotificationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ProductOfferingNotificationCreator extends AbstractResourceNotificationCreator<ProductOffering> {

    private static final String PAYLOAD_NAME = "productOffering";

    protected ProductOfferingNotificationCreator(ObjectMapper mapper) {

        super(PAYLOAD_NAME, "ProductOffering", mapper);
    }

    @Override
    public List<Notification> updateNotifications(Map<String, Object> changeReq, ProductOffering original,
                                                  ProductOffering merged, ProductOffering updated) {

        ArrayList<Notification> notifications = new ArrayList<>();

        if (!changeReq.isEmpty() && !Objects.equals(original, merged)) {
            notifications.add(NotificationUtils.createNotification("ProductOfferingAttributeValueChangeEvent",
                    PAYLOAD_NAME, updated));
        }

        if (changeReq.containsKey("lifecycleStatus") && !Objects.equals(changeReq.get("lifecycleStatus"), original.getLifecycleStatus())) {

            notifications.add(NotificationUtils.createNotification("ProductOfferingStateChangeEvent",
                    PAYLOAD_NAME, updated));
        }

        return notifications;
    }

    @Override
    public List<Notification> updateNotifications(List<PatchDef> changeReq, ProductOffering original,
                                                  ProductOffering merged, ProductOffering updated) {

        ArrayList<Notification> notifications = new ArrayList<>();

        if (!changeReq.isEmpty() && !Objects.equals(original, merged)) {
            notifications.add(NotificationUtils.createNotification("ProductOfferingAttributeValueChangeEvent",
                    PAYLOAD_NAME, updated));
        }

        if (!Objects.equals(original.getLifecycleStatus(), updated.getLifecycleStatus())) {

            notifications.add(NotificationUtils.createNotification("ProductOfferingStateChangeEvent",
                    PAYLOAD_NAME, updated));
        }

        return notifications;
    }
}
