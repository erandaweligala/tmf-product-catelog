package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Catalog;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class CatalogService extends BaseResourceService<Catalog> {

    protected CatalogService(ResourceRepositoryInterface<Catalog> repository,
                             MergeAdaptorInterface mergeAdaptor,
                             ResourceProcessor<Catalog> processor,
                             ResourceNotificationCreator<Catalog> notificationCreator,
                             NotifierAdaptorInterface notifier, HttpServletRequest httpServletRequest) {

        super(repository, "CATALOG", mergeAdaptor, processor, Catalog.class, notificationCreator, notifier, httpServletRequest);
    }
}
