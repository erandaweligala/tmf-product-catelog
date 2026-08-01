package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.*;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import com.adl.et.telco.mvno.productcatalog.domain.notification.ProductSpecificationNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.processor.ProductSpecificationProcessor;
import com.adl.et.telco.mvno.productcatalog.external.repositories.ProductSpecificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

class ProductSpecificationServiceTest {

    ProductSpecificationService service;
    ResourceRepositoryInterface<ProductSpecification> mockRepository;
    MergeAdaptorInterface mockMergeAdaptor;
    ResourceProcessor<ProductSpecification> mockProcessor;
    ResourceNotificationCreator<ProductSpecification> mockNotificationCreator;
    NotifierAdaptorInterface mockNotifierAdaptor;
    HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(ProductSpecificationRepository.class);
        mockMergeAdaptor = Mockito.mock(MergeAdaptorInterface.class);
        mockProcessor = Mockito.mock(ProductSpecificationProcessor.class);
        mockNotificationCreator = Mockito.mock(ProductSpecificationNotificationCreator.class);
        mockNotifierAdaptor = Mockito.mock(NotifierAdaptorInterface.class);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);

        service = new ProductSpecificationService(mockRepository, mockMergeAdaptor, mockProcessor,
                mockNotificationCreator, mockNotifierAdaptor, httpServletRequest);
    }

    @Test
    void createTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setName("name");

        ProductSpecification saved = new ProductSpecification();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockRepository.save(Mockito.argThat(spec -> "name".equals(spec.getName()))))
                .thenReturn(saved);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.createNotifications(Mockito.any(ProductSpecification.class)))
                .thenReturn(Collections.singletonList(notification));

        ProductSpecification result = service.create(specification);

        Assertions.assertEquals(saved, result);

        Mockito.verify(mockProcessor, Mockito.times(1)).preCreateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);
    }

    @Test
    void updateTest() {

        ProductSpecification saved = new ProductSpecification();
        saved.setName("name");
        saved.setId("id");

        Map<String, Object> updateReq = new HashMap<>();
        updateReq.put("name", "name2");
        updateReq.put("description", "desc");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(saved));

        ProductSpecification merged = new ProductSpecification();
        merged.setName("name");
        merged.setDescription("desc");
        merged.setId("id");

        Mockito.when(mockMergeAdaptor.mergePatch(updateReq, saved, ProductSpecification.class))
                .thenReturn(merged);

        Mockito.when(mockRepository.save(merged))
                .thenReturn(merged);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.updateNotifications(Mockito.eq(updateReq),
                Mockito.any(ProductSpecification.class), Mockito.any(ProductSpecification.class),
                Mockito.any(ProductSpecification.class)))
                .thenReturn(Collections.singletonList(notification));

        ProductSpecification result = service.update("id", updateReq);

        Assertions.assertEquals(merged, result);

        Mockito.verify(mockProcessor, Mockito.times(1))
                .preMergeProcess(Mockito.any(Map.class), Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).preUpdateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);
    }

    @Test
    void queryTest() {

        List<Filter> filters = Collections
                .singletonList(new Filter("name", new Object[]{"name"}, FilterOperation.IS));
        String fields = "name,description";

        ProductSpecification specification1 = new ProductSpecification();
        specification1.setId("id1");
        ProductSpecification specification2 = new ProductSpecification();
        specification2.setId("id2");

        Mockito.when(mockRepository.query(filters, fields, new Pageable()))
                .thenReturn(new Page<>(Arrays.asList(specification1, specification2), 2));

        Page<ProductSpecification> page = service.query(filters, fields, new Pageable());

        Assertions.assertEquals(2, page.getTotal());
        Assertions.assertEquals(2, page.getContent().size());

        Mockito.verify(mockProcessor, Mockito.times(2)).postProcess(Mockito.any());
    }

    @Test
    void getTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setId("id");
        specification.setName("name");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(specification));

        ProductSpecification saved = service.get("id");

        Assertions.assertEquals("name", saved.getName());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
    }

    @Test
    void deleteTest() {

        ProductSpecification specification = new ProductSpecification();
        specification.setId("id");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(specification));

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.deleteNotifications(specification))
                .thenReturn(Collections.singletonList(notification));

        service.delete("id");

        Mockito.verify(mockRepository, Mockito.times(1)).delete("id");
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);
    }
}