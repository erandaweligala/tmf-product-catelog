package com.adl.et.telco.mvno.productcatalog.domain.processor;

import static org.junit.jupiter.api.Assertions.*;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.JsonSubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.*;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.LifecycleStatus;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

class ProductOfferingPriceProcessorTest {

    private ProductOfferingPriceProcessor processor;

    @Mock
    private JsonSubResourceRepositoryInterface mockJsonSubResourceRepository;
    @Mock
    private ResourceRepositoryInterface<ProductSpecification> mockProductSpecificationRepository;
    @Mock
    private ResourceRepositoryInterface<ProductOfferingPrice> mockProductOfferingPriceRepository;
    @Mock
    private ProcessorUtils mockProcessorUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        processor = new ProductOfferingPriceProcessor(
                mock(UrlResolverAdaptorInterface.class),
                mockJsonSubResourceRepository,
                mockProductSpecificationRepository,
                mockProductOfferingPriceRepository,
                mockProcessorUtils
        );
    }

    @Test
    void validateWithBundleProductOfferingPrice() {
        // Prepare
        ProductOfferingPrice offeringPrice = new ProductOfferingPrice();
        offeringPrice.setIsBundle(true);
        offeringPrice.setBundledPopRelationship(Collections.singletonList(new BundledProductOfferingPriceRelationship()));

        // Execute & Verify
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> processor.validate(offeringPrice));
    }

    @Test
    void validateWithNonBundleProductOfferingPrice() {
        // Prepare
        ProductOfferingPrice offeringPrice = new ProductOfferingPrice();
        offeringPrice.setIsBundle(false);
        offeringPrice.setPriceType("testPriceType");

        // Execute & Verify
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> processor.validate(offeringPrice));
    }

    @Test
    void validateWithInvalidBundlePopRelationship() {
        // Prepare
        ProductOfferingPrice offeringPrice = new ProductOfferingPrice();
        offeringPrice.setIsBundle(false);
        offeringPrice.setBundledPopRelationship(Collections.singletonList(new BundledProductOfferingPriceRelationship()));

        // Execute & Verify
        org.junit.jupiter.api.Assertions.assertThrows(DomainException.class, () -> processor.validate(offeringPrice));
    }

    @Test
    void validateWithMissingPriceType() {
        // Prepare
        ProductOfferingPrice offeringPrice = new ProductOfferingPrice();
        offeringPrice.setIsBundle(false);

        // Execute & Verify
        org.junit.jupiter.api.Assertions.assertThrows(DomainException.class, () -> processor.validate(offeringPrice));
    }


}