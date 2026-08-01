package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.*;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import com.adl.et.telco.mvno.productcatalog.external.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

class ProductOfferingProcessorTest {

    private UrlResolverAdaptorInterface mockUrlResolver;
    private SubResourceRepositoryInterface<AttachmentRefOrValueDocument> mockAttachmentRepository;
    private ResourceRepositoryInterface<Category> mockCategoryRepository;
    private ResourceRepositoryInterface<ProductOffering> mockProductOfferingRepository;
    private ResourceRepositoryInterface<ProductOfferingPrice> mockProductOfferingPriceRepository;
    private ResourceRepositoryInterface<ProductSpecification> mockProductSpecificationRepository;
    private ProcessorUtils mockProcessorUtils;

    ProductOfferingProcessor processor;

    @BeforeEach
    void setup() {
        mockUrlResolver = Mockito.mock(UrlResolverAdaptorInterface.class);
        mockAttachmentRepository = Mockito.mock(AttachmentRepository.class);
        mockCategoryRepository = Mockito.mock(CategoryRepository.class);
        mockProductOfferingRepository = Mockito.mock(ProductOfferingRepository.class);
        mockProductOfferingPriceRepository = Mockito.mock(ProductOfferingPriceRepository.class);
        mockProductSpecificationRepository = Mockito.mock(ProductSpecificationRepository.class);
        mockProcessorUtils = Mockito.mock(ProcessorUtils.class);

        processor = new ProductOfferingProcessor(mockUrlResolver, mockAttachmentRepository,
                mockCategoryRepository, mockProductOfferingRepository,
                mockProductOfferingPriceRepository, mockProductSpecificationRepository,
                mockProcessorUtils);
    }

    @Test
    void validateBundleProductOfferingWithNoBundleOfferingsTest() {
        ProductOffering offering = new ProductOffering();
        offering.setIsBundle(true);

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> processor.validate(offering)
        );

        Assertions.assertEquals("NO_BUNDLE_OFFERINGS", thrown.getCode());
    }

    @Test
    void validateNonBundleProductOfferingWithoutProductSpecificationTest() {
        ProductOffering offering = new ProductOffering();
        offering.setIsBundle(false);

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> processor.validate(offering)
        );

        Assertions.assertEquals("INVALID_BUNDLE_SPEC", thrown.getCode());
    }

    @Test
    void validateNonBundleProductOfferingWithBundleOfferingsTest() {
        ProductOffering offering = new ProductOffering();
        offering.setIsBundle(false);
        offering.setBundledProductOffering(Collections.singletonList(new BundledProductOffering()));

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> processor.validate(offering)
        );

        Assertions.assertEquals("INVALID_BUNDLE_SPEC", thrown.getCode());
    }

}