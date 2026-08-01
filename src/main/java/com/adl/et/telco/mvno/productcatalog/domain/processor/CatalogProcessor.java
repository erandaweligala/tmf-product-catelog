package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Catalog;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.LifecycleStatus;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import org.springframework.stereotype.Component;

@Component
public class CatalogProcessor extends AbstractResourceProcessor<Catalog> {

    private final ResourceRepositoryInterface<Category> categoryRepository;
    private final ProcessorUtils processorUtils;

    protected CatalogProcessor(UrlResolverAdaptorInterface urlResolver,
                               ResourceRepositoryInterface<Category> categoryRepository, ProcessorUtils processorUtils) {

        super(urlResolver);
        this.categoryRepository = categoryRepository;
        this.processorUtils = processorUtils;
    }

    @Override
    protected void validate(Catalog category) {

        processorUtils.checkRef(category.getCategory(), Category.class, categoryRepository);

        if (category.getId() == null) {
            category.setLifecycleStatus(LifecycleStatus.ACTIVE);
        }
    }
}
