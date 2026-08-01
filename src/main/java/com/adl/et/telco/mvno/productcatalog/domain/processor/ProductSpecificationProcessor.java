package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.AttachmentRefOrValueDocument;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.LifecycleStatus;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductSpecificationProcessor extends AbstractResourceProcessor<ProductSpecification> {

    private final SubResourceRepositoryInterface<AttachmentRefOrValueDocument> attachmentRepository;
    private final ResourceRepositoryInterface<ProductSpecification> productSpecificationRepository;
    private final ProcessorUtils processorUtils;

    protected ProductSpecificationProcessor(UrlResolverAdaptorInterface urlResolver,
                                            SubResourceRepositoryInterface<AttachmentRefOrValueDocument> attachmentRepository,
                                            ResourceRepositoryInterface<ProductSpecification> productSpecificationRepository,
                                            ProcessorUtils processorUtils) {

        super(urlResolver);

        this.attachmentRepository = attachmentRepository;
        this.productSpecificationRepository = productSpecificationRepository;
        this.processorUtils = processorUtils;
    }

    @Override
    protected void validate(ProductSpecification specification) {
        if (Boolean.TRUE.equals(specification.getIsBundle())) {

            if (specification.getBundledProductSpecification() == null ||
                    specification.getBundledProductSpecification().isEmpty()) {

                throw new DomainException(
                        "Should have at least one bundledProductSpecification if product specification is a bundle",
                        "NO_BUNDLE_SPECS");
            }

        } else if (specification.getBundledProductSpecification() != null &&
                !specification.getBundledProductSpecification().isEmpty()) {
            throw new DomainException(
                    "Should not have bundledProductSpecification if product specification is not a bundle",
                    "INVALID_BUNDLE_SPECS");
        }

        processorUtils.checkRef(specification.getBundledProductSpecification(), ProductSpecification.class,
                productSpecificationRepository);
        processorUtils.checkRef(specification.getProductSpecificationRelationship(), ProductSpecification.class,
                productSpecificationRepository);
        if (specification.getProductSpecCharacteristic() != null) {

            specification.getProductSpecCharacteristic().stream()
                    .filter(characteristic -> characteristic.getProductSpecCharacteristicValue() != null)
                    .forEach(characteristic -> characteristic.getProductSpecCharacteristicValue().stream()
                            .filter(value -> value.getValue() instanceof Map)
                            .map(value -> (Map<String, Object>) (value.getValue()))
                            .forEach(json -> processorUtils.validateBySchema(json, "ValueType",
                                    characteristic.getValueType(),
                                    "productSpecCharacteristic->productSpecCharacteristicValue->value")));
        }
        processorUtils.updateRefAndDocument(specification.getAttachment(),
                AttachmentRefOrValueDocument.class, attachmentRepository);

        if (specification.getId() == null) {
            specification.setLifecycleStatus(LifecycleStatus.DRAFT);
        }
    }
}
