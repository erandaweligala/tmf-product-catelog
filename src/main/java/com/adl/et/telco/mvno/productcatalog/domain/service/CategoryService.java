package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class CategoryService extends BaseResourceService<Category> {

    protected CategoryService(ResourceRepositoryInterface<Category> repository,
                              MergeAdaptorInterface mergeAdaptor,
                              ResourceProcessor<Category> processor,
                              ResourceNotificationCreator<Category> notificationCreator,
                              NotifierAdaptorInterface notifier, HttpServletRequest httpServletRequest) {

        super(repository, "CATEGORY", mergeAdaptor, processor, Category.class, notificationCreator, notifier, httpServletRequest);
    }
}
