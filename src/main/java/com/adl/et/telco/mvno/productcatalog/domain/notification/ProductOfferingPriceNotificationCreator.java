package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.AbstractResourceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import com.adl.et.telco.mvno.productcatalog.domain.notification.utils.NotificationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ProductOfferingPriceNotificationCreator extends AbstractResourceNotificationCreator<ProductOfferingPrice> {

    private static final String PAYLOAD_NAME = "productOfferingPrice";

    protected ProductOfferingPriceNotificationCreator(ObjectMapper mapper) {

        super(PAYLOAD_NAME, "ProductOfferingPrice", mapper);
    }

    @Override
    public List<Notification> updateNotifications(Map<String, Object> changeReq, ProductOfferingPrice original,
                                                  ProductOfferingPrice merged, ProductOfferingPrice updated) {

        return updateNotifications(original, merged, updated);
    }

    @Override
    public List<Notification> updateNotifications(List<PatchDef> changeReq, ProductOfferingPrice original,
                                                  ProductOfferingPrice merged, ProductOfferingPrice updated) {

        return updateNotifications(original, merged, updated);
    }

    List<Notification> updateNotifications(ProductOfferingPrice original,
                                           ProductOfferingPrice merged, ProductOfferingPrice updated) {

        ArrayList<Notification> notifications = new ArrayList<>();

        if (!Objects.equals(original, merged)) {

            notifications.add(NotificationUtils.createNotification("ProductOfferingPriceAttributeValueChangeEvent",
                    PAYLOAD_NAME, updated));
        }

        if (!Objects.equals(original.getLifecycleStatus(), updated.getLifecycleStatus())) {

            notifications.add(NotificationUtils.createNotification("ProductOfferingPriceStateChangeEvent",
                    PAYLOAD_NAME, updated));
        }

        return notifications;
    }
}
