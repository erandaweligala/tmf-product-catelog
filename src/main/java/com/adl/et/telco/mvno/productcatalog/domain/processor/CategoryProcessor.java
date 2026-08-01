package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.LifecycleStatus;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryProcessor extends AbstractResourceProcessor<Category> {

    private final ProcessorUtils processorUtils;
    private final ResourceRepositoryInterface<Category> categoryRepository;
    private final ResourceRepositoryInterface<ProductOffering> productOfferingRepository;

    protected CategoryProcessor(UrlResolverAdaptorInterface urlResolver,
                                ProcessorUtils processorUtils,
                                ResourceRepositoryInterface<Category> categoryRepository,
                                ResourceRepositoryInterface<ProductOffering> productOfferingRepository) {

        super(urlResolver);
        this.processorUtils = processorUtils;
        this.categoryRepository = categoryRepository;
        this.productOfferingRepository = productOfferingRepository;
    }

    @Override
    protected void validate(Category category) {

        if (Boolean.TRUE.equals(category.getIsRoot())) {
            if (category.getParentId() != null) {
                throw new DomainException("Should not have parentId for root category",
                        "INVALID_CATEGORY_PARENT");
            }
        } else if (category.getParentId() == null || category.getParentId().isEmpty()) {
            throw new DomainException("Should define parentId for non root category",
                    "NO_CATEGORY_PARENT");
        }

        processorUtils.checkRef(category.getSubCategory(), Category.class, categoryRepository);
        processorUtils.checkRef(category.getProductOffering(), ProductOffering.class, productOfferingRepository);

        if (category.getId() == null) {
            category.setLifecycleStatus(LifecycleStatus.ACTIVE);
        }
    }
}
