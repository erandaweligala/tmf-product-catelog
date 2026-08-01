package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class PricingLogicAlgorithmSpecificationService extends BaseResourceService<PricingLogicAlgorithm> {

    public PricingLogicAlgorithmSpecificationService(ResourceRepositoryInterface<PricingLogicAlgorithm> repository,
                                                     MergeAdaptorInterface mergeAdaptor,
                                                     ResourceProcessor<PricingLogicAlgorithm> processor,
                                                     ResourceNotificationCreator<PricingLogicAlgorithm> notificationCreator,
                                                     NotifierAdaptorInterface notifier, HttpServletRequest httpServletRequest) {

        super(repository, "PRICING_LOGIC_ALGORITHM", mergeAdaptor, processor, PricingLogicAlgorithm.class,
                notificationCreator, notifier, httpServletRequest);
    }
}
