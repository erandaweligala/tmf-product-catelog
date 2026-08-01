package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.*;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.LifecycleStatus;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductOfferingProcessor extends AbstractResourceProcessor<ProductOffering> {

    private final SubResourceRepositoryInterface<AttachmentRefOrValueDocument> attachmentRepository;
    private final ResourceRepositoryInterface<Category> categoryRepository;
    private final ResourceRepositoryInterface<ProductOffering> productOfferingRepository;
    private final ResourceRepositoryInterface<ProductOfferingPrice> productOfferingPriceRepository;
    private final ResourceRepositoryInterface<ProductSpecification> productSpecificationRepository;
    private final ProcessorUtils processorUtils;
    private final UrlResolverAdaptorInterface urlResolver;

    protected ProductOfferingProcessor(UrlResolverAdaptorInterface urlResolver,
                                       SubResourceRepositoryInterface<AttachmentRefOrValueDocument> attachmentRepository,
                                       ResourceRepositoryInterface<Category> categoryRepository,
                                       ResourceRepositoryInterface<ProductOffering> productOfferingRepository,
                                       ResourceRepositoryInterface<ProductOfferingPrice> productOfferingPriceRepository,
                                       ResourceRepositoryInterface<ProductSpecification> productSpecificationRepository,
                                       ProcessorUtils processorUtils) {

        super(urlResolver);

        this.attachmentRepository = attachmentRepository;
        this.categoryRepository = categoryRepository;
        this.productOfferingRepository = productOfferingRepository;
        this.productOfferingPriceRepository = productOfferingPriceRepository;
        this.productSpecificationRepository = productSpecificationRepository;
        this.processorUtils = processorUtils;
        this.urlResolver = urlResolver;
    }

    @Override
    protected void validate(ProductOffering offering) {
        if (Boolean.TRUE.equals(offering.getIsBundle())) {

            if (offering.getBundledProductOffering() == null ||
                    offering.getBundledProductOffering().isEmpty()) {

                throw new DomainException(
                        "Should have at least one bundledProductOffering if product offering is a bundle",
                        "NO_BUNDLE_OFFERINGS");
            }

        } else {
            if (offering.getProductSpecification() == null) {
                throw new DomainException(
                        "Should have productSpecification if product offering is not a bundle",
                        "INVALID_BUNDLE_SPEC");
            }
            if (offering.getBundledProductOffering() != null &&
                    !offering.getBundledProductOffering().isEmpty()) {
                throw new DomainException(
                        "Should not have bundledProductOffering if product offering is not a bundle",
                        "INVALID_BUNDLE_OFFERINGS");
            }
        }

        processorUtils.checkRef(offering.getCategory(), Category.class, categoryRepository);
        processorUtils.checkRef(offering.getBundledProductOffering(), ProductOffering.class, productOfferingRepository);
        processorUtils.checkRef(offering.getProductOfferingPrice(), ProductOfferingPrice.class,
                productOfferingPriceRepository);
        processorUtils.checkRef(offering.getProductSpecification(), ProductSpecification.class,
                productSpecificationRepository);

        if (offering.getProdSpecCharValueUse() != null) {
            offering.getProdSpecCharValueUse().stream()
                    .filter(valueUse -> valueUse.getProductSpecCharacteristicValue() != null)
                    .forEach(valueUse -> valueUse.getProductSpecCharacteristicValue().stream()
                            .filter(value -> value.getValue() instanceof Map)
                            .map(value -> (Map<String, Object>) (value.getValue()))
                            .forEach(json -> processorUtils.validateBySchema(json, "ValueType", valueUse.getValueType(),
                                    "prodSpecCharValueUse->productSpecCharacteristicValue->value")));
        }

        processorUtils.updateRefAndDocument(offering.getAttachment(), AttachmentRefOrValueDocument.class,
                attachmentRepository);
        updateCharValues(offering);

//        if (offering.getId() == null) {
//            offering.setLifecycleStatus(LifecycleStatus.ACTIVE);
//        }
    }

    private void updateCharValues(ProductOffering offering) {

        if (offering.getProductSpecification() == null || offering.getProductSpecification().getId() == null) {
            return;
        }

        if (offering.getProdSpecCharValueUse() == null) {

            offering.setProdSpecCharValueUse(new ArrayList<>());
        }

        Optional<ProductSpecification> productSpecification = productSpecificationRepository.get(offering.getProductSpecification().getId());
        ProductSpecification spec = new ProductSpecification();
        if (productSpecification.isPresent()){
            spec = productSpecification.get();
        }

        ProductSpecificationRef ref = createRefFromSpec(spec);

        spec.getProductSpecCharacteristic().forEach(characteristic -> {

            if (offering.getProdSpecCharValueUse().stream()
                    .noneMatch(valueUse -> Objects.equals(valueUse.getName(), characteristic.getName()))) {

                ProductSpecificationCharacteristicValueUse valueUse = new ProductSpecificationCharacteristicValueUse();

                copyBasicsToValueUse(valueUse, characteristic);
                valueUse.setProductSpecification(ref);

                if (characteristic.getMaxCardinality() >= 1) {

                    List<ProductSpecificationCharacteristicValue> values = new ArrayList<>();

                    if (characteristic.getProductSpecCharacteristicValue() != null) {

                        characteristic.getProductSpecCharacteristicValue()
                                .stream()
                                .filter(value -> Boolean.TRUE.equals(value.getIsDefault()))
                                .findFirst().ifPresent(values::add);

                        values.addAll(characteristic.getProductSpecCharacteristicValue()
                                .stream()
                                .filter(value -> Boolean.FALSE.equals(value.getIsDefault()))
                                .limit(characteristic.getMaxCardinality().longValue() - values.size())
                                .collect(Collectors.toList()));
                    }
                    valueUse.setProductSpecCharacteristicValue(values);
                } else {
                    valueUse.setProductSpecCharacteristicValue(characteristic.getProductSpecCharacteristicValue());
                }

                offering.getProdSpecCharValueUse().add(valueUse);
            }
        });
    }

    private ProductSpecificationRef createRefFromSpec(ProductSpecification spec) {

        ProductSpecificationRef ref = new ProductSpecificationRef();

        ref.setId(spec.getId());
        ref.setName(spec.getName());
        ref.setVersion(spec.getVersion());
        ref.setHref(urlResolver.createHref(spec));

        return ref;
    }

    private void copyBasicsToValueUse(ProductSpecificationCharacteristicValueUse valueUse,
                                      ProductSpecificationCharacteristic characteristic) {

        valueUse.setName(characteristic.getName());
        valueUse.setDescription(characteristic.getDescription());
        valueUse.setMaxCardinality(characteristic.getMaxCardinality());
        valueUse.setMinCardinality(characteristic.getMinCardinality());
        valueUse.setValueType(characteristic.getValueType());
        valueUse.setValidFor(characteristic.getValidFor());
        valueUse.setCharType(characteristic.getCharType());
    }
}
