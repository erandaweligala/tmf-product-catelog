package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


@Service
public class ProductOfferingService extends BaseResourceService<ProductOffering> {

    public ProductOfferingService(ResourceRepositoryInterface<ProductOffering> repository,
                                  MergeAdaptorInterface mergeAdaptor,
                                  ResourceProcessor<ProductOffering> processor,
                                  ResourceNotificationCreator<ProductOffering> notificationCreator,
                                  NotifierAdaptorInterface notifier,
                                  HttpServletRequest httpServletRequest) {

        super(repository, "PRODUCT_OFFERING", mergeAdaptor, processor, ProductOffering.class,
                notificationCreator, notifier,httpServletRequest);
    }
}
