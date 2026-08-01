package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProductOfferingPriceService extends BaseResourceService<ProductOfferingPrice> {

    public ProductOfferingPriceService(ResourceRepositoryInterface<ProductOfferingPrice> repository,
                                       MergeAdaptorInterface mergeAdaptor,
                                       ResourceProcessor<ProductOfferingPrice> processor,
                                       ResourceNotificationCreator<ProductOfferingPrice> notificationCreator,
                                       NotifierAdaptorInterface notifier, HttpServletRequest httpServletRequest) {

        super(repository, "PRODUCT_OFFERING_PRICE", mergeAdaptor, processor, ProductOfferingPrice.class,
                notificationCreator, notifier, httpServletRequest);
    }
}
