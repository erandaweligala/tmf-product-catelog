package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class ProductSpecificationService extends BaseResourceService<ProductSpecification> {

    public ProductSpecificationService(ResourceRepositoryInterface<ProductSpecification> repository,
                                       MergeAdaptorInterface mergeAdaptor,
                                       ResourceProcessor<ProductSpecification> processor,
                                       ResourceNotificationCreator<ProductSpecification> notificationCreator,
                                       NotifierAdaptorInterface notifier, HttpServletRequest httpServletRequest) {

        super(repository, "PRODUCT_SPEC", mergeAdaptor, processor, ProductSpecification.class,
                notificationCreator, notifier, httpServletRequest);
    }
}
