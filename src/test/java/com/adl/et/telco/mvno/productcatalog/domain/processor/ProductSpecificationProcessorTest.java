package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.*;
import com.adl.et.telco.mvno.productcatalog.domain.processor.utils.ProcessorUtils;
import com.adl.et.telco.mvno.productcatalog.external.repositories.AttachmentRepository;
import com.adl.et.telco.mvno.productcatalog.external.repositories.ProductSpecificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class ProductSpecificationProcessorTest {

    private UrlResolverAdaptorInterface mockUrlResolver;
    private SubResourceRepositoryInterface<AttachmentRefOrValueDocument> mockAttachmentRepository;
    private ResourceRepositoryInterface<ProductSpecification> mockProductSpecificationRepository;
    private ProcessorUtils mockProcessorUtils;

    ProductSpecificationProcessor processor;

    @BeforeEach
    void setup() {

        mockUrlResolver = Mockito.mock(UrlResolverAdaptorInterface.class);
        mockAttachmentRepository = Mockito.mock(AttachmentRepository.class);
        mockProductSpecificationRepository = Mockito.mock(ProductSpecificationRepository.class);
        mockProcessorUtils = Mockito.mock(ProcessorUtils.class);

        processor = new ProductSpecificationProcessor(mockUrlResolver, mockAttachmentRepository,
                mockProductSpecificationRepository, mockProcessorUtils);
    }

    @Test
    void preUpdateProcessValidTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setIsBundle(true);

        BundledProductSpecification bundled = new BundledProductSpecification();
        bundled.setId("bundledId");

        specification.setBundledProductSpecification(Collections.singletonList(bundled));

        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setEndDateTime(OffsetDateTime.now().plusDays(2));
        timePeriod.setStartDateTime(OffsetDateTime.now());

        specification.setValidFor(timePeriod);

        AttachmentRefOrValue attachmentRefOrValue = new AttachmentRefOrValue();
        attachmentRefOrValue.setName("attachmentName");

        specification.setAttachment(Collections.singletonList(attachmentRefOrValue));

        AttachmentRefOrValueDocument attachmentDocument = new AttachmentRefOrValueDocument();
        attachmentDocument.setName("attachmentName");

        processor.preUpdateProcess(specification);

        Assertions.assertNotNull(specification.getLastUpdate());

        Mockito.verify(mockProcessorUtils, Mockito.times(1)).updateRefAndDocument(Mockito.anyList(), Mockito.eq(AttachmentRefOrValueDocument.class), Mockito.any());
    }

    @Test
    void preUpdateProcessInvalidTimeTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setIsBundle(true);

        BundledProductSpecification bundled = new BundledProductSpecification();
        bundled.setId("bundledId");

        specification.setBundledProductSpecification(Collections.singletonList(bundled));

        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setEndDateTime(OffsetDateTime.now());
        timePeriod.setStartDateTime(OffsetDateTime.now().plusDays(2));

        specification.setValidFor(timePeriod);

        AttachmentRefOrValue attachmentRefOrValue = new AttachmentRefOrValue();
        attachmentRefOrValue.setName("attachmentName");

        specification.setAttachment(Collections.singletonList(attachmentRefOrValue));

        AttachmentRefOrValueDocument attachmentDocument = new AttachmentRefOrValueDocument();
        attachmentDocument.setName("attachmentName");

        AttachmentRefOrValueDocument savedAttachmentDocument = new AttachmentRefOrValueDocument();
        savedAttachmentDocument.setName("attachmentName");
        savedAttachmentDocument.setId("attId");

        Mockito.when(mockAttachmentRepository.save(attachmentDocument))
                .thenReturn(savedAttachmentDocument);

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> processor.preUpdateProcess(specification)
        );

        Assertions.assertEquals("INVALID_TIME_PERIOD", thrown.getCode());
    }

    @Test
    void preUpdateProcessInvalidBundledTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setIsBundle(true);

        specification.setBundledProductSpecification(Collections.emptyList());

        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setEndDateTime(OffsetDateTime.now().plusDays(2));
        timePeriod.setStartDateTime(OffsetDateTime.now());

        specification.setValidFor(timePeriod);

        AttachmentRefOrValue attachmentRefOrValue = new AttachmentRefOrValue();
        attachmentRefOrValue.setName("attachmentName");

        specification.setAttachment(Collections.singletonList(attachmentRefOrValue));

        AttachmentRefOrValueDocument attachmentDocument = new AttachmentRefOrValueDocument();
        attachmentDocument.setName("attachmentName");

        AttachmentRefOrValueDocument savedAttachmentDocument = new AttachmentRefOrValueDocument();
        savedAttachmentDocument.setName("attachmentName");
        savedAttachmentDocument.setId("attId");

        Mockito.when(mockAttachmentRepository.save(attachmentDocument))
                .thenReturn(savedAttachmentDocument);

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> processor.preUpdateProcess(specification)
        );

        Assertions.assertEquals("NO_BUNDLE_SPECS", thrown.getCode());
    }

    @Test
    void preUpdateProcessInvalidNotBundledTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setIsBundle(false);

        BundledProductSpecification bundled = new BundledProductSpecification();
        bundled.setId("bundledId");

        specification.setBundledProductSpecification(Collections.singletonList(bundled));

        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setEndDateTime(OffsetDateTime.now().plusDays(2));
        timePeriod.setStartDateTime(OffsetDateTime.now());

        specification.setValidFor(timePeriod);

        AttachmentRefOrValue attachmentRefOrValue = new AttachmentRefOrValue();
        attachmentRefOrValue.setName("attachmentName");

        specification.setAttachment(Collections.singletonList(attachmentRefOrValue));

        AttachmentRefOrValueDocument attachmentDocument = new AttachmentRefOrValueDocument();
        attachmentDocument.setName("attachmentName");

        AttachmentRefOrValueDocument savedAttachmentDocument = new AttachmentRefOrValueDocument();
        savedAttachmentDocument.setName("attachmentName");
        savedAttachmentDocument.setId("attId");

        Mockito.when(mockAttachmentRepository.save(attachmentDocument))
                .thenReturn(savedAttachmentDocument);

        DomainException thrown = Assertions.assertThrows(
                DomainException.class,
                () -> processor.preUpdateProcess(specification)
        );

        Assertions.assertEquals("INVALID_BUNDLE_SPECS", thrown.getCode());
    }

    @Test
    void preMergeProcessTest() {

        Map<String, Object> updateReq = new HashMap<>();
        updateReq.put("name", "name");
        updateReq.put("version", "2.5");
        updateReq.put("id", "id");

        ProductSpecification existing = new ProductSpecification();
        existing.setId("id");
        existing.setVersion("2.1");

        processor.preMergeProcess(updateReq, existing);

        Assertions.assertFalse(updateReq.containsKey("id"));
    }

    @Test
    void postProcessTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setId("id");


        Mockito.when(mockUrlResolver.createHref(specification)).thenReturn("productSpecification/id");

        processor.postProcess(specification);

        Assertions.assertNotNull(specification.getHref());
        Assertions.assertEquals("productSpecification/id", specification.getHref());
    }
}