package com.adl.et.telco.mvno.productcatalog.domain.notification;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Notification;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class PricingLogicAlgorithmNotificationCreator implements ResourceNotificationCreator<PricingLogicAlgorithm> {

    @Override
    public List<Notification> createNotifications(PricingLogicAlgorithm entity) {
        return Collections.emptyList();
    }

    @Override
    public List<Notification> updateNotifications(Map<String, Object> changeReq, PricingLogicAlgorithm original,
                                                  PricingLogicAlgorithm merged, PricingLogicAlgorithm updated) {
        return Collections.emptyList();
    }

    @Override
    public List<Notification> updateNotifications(List<PatchDef> changeReq, PricingLogicAlgorithm original,
                                                  PricingLogicAlgorithm merged, PricingLogicAlgorithm updated) {
        return Collections.emptyList();
    }

    @Override
    public List<Notification> deleteNotifications(PricingLogicAlgorithm entity) {
        return Collections.emptyList();
    }
}
