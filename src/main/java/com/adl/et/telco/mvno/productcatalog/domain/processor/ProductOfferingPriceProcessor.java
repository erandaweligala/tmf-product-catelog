package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.JsonSubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecificationCharacteristicValueUse;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.LifecycleStatus;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductOfferingPriceProcessor extends AbstractResourceProcessor<ProductOfferingPrice> {

    private final JsonSubResourceRepositoryInterface jsonSubResourceRepository;
    private final ResourceRepositoryInterface<ProductSpecification> productSpecificationRepository;
    private final ResourceRepositoryInterface<ProductOfferingPrice> productOfferingPriceRepository;
    private final ProcessorUtils processorUtils;

    protected ProductOfferingPriceProcessor(UrlResolverAdaptorInterface urlResolver,
                                            JsonSubResourceRepositoryInterface jsonSubResourceRepository,
                                            ResourceRepositoryInterface<ProductSpecification> productSpecificationRepository,
                                            ResourceRepositoryInterface<ProductOfferingPrice> productOfferingPriceRepository,
                                            ProcessorUtils processorUtils) {

        super(urlResolver);

        this.jsonSubResourceRepository = jsonSubResourceRepository;
        this.productSpecificationRepository = productSpecificationRepository;
        this.productOfferingPriceRepository = productOfferingPriceRepository;
        this.processorUtils = processorUtils;
    }

    @Override
    protected void validate(ProductOfferingPrice entity) {

        entity.setBaseType("ProductOfferingPrice");
        if(entity.getPriceType() != null) {
            entity.setType(entity.getPriceType().substring(0, 1).toUpperCase() + entity.getPriceType().substring(1) +
                    "ProductOfferingPrice");
        }

        if (Boolean.TRUE.equals(entity.getIsBundle())) {

            if (entity.getBundledPopRelationship() == null ||
                    entity.getBundledPopRelationship().isEmpty()) {

                throw new DomainException(
                        "Should have at least one bundledPopRelationship if product offering price is a bundle",
                        "NO_BUNDLE_POP_RELATIONSHIP");
            }

        } else {
            if (entity.getPriceType() == null) {
                throw new DomainException(
                        "Should have priceType if product offering price is not a bundle",
                        "NO_PRICE_TYPE");
            }
            if (entity.getBundledPopRelationship() != null &&
                    !entity.getBundledPopRelationship().isEmpty()) {
                throw new DomainException(
                        "Should not have bundledPopRelationship if product offering price is not a bundle",
                        "INVALID_POP_RELATIONSHIP");
            }
        }

        if (entity.getProdSpecCharValueUse() != null) {
            processorUtils.checkRef(entity.getProdSpecCharValueUse()
                    .stream()
                    .map(ProductSpecificationCharacteristicValueUse::getProductSpecification)
                    .collect(Collectors.toList()), ProductSpecification.class, productSpecificationRepository);
        }
        processorUtils.checkRef(entity.getBundledPopRelationship(), ProductOfferingPrice.class,
                productOfferingPriceRepository);
        processorUtils.checkRef(entity.getPopRelationship(), ProductOfferingPrice.class,
                productOfferingPriceRepository);

        if (entity.getPricingLogicAlgorithm() != null) {
            entity.getPricingLogicAlgorithm()
                    .forEach(algorithm -> processorUtils.validateBySchema(algorithm, "pricingLogicAlgorithm"));
        }
        processorUtils.updateRefAndDocument(entity.getPricingLogicAlgorithm(),
                "pricingLogicAlgorithm", jsonSubResourceRepository);

        if (entity.getId() == null) {
            entity.setLifecycleStatus(LifecycleStatus.ACTIVE);
        }
    }
}
