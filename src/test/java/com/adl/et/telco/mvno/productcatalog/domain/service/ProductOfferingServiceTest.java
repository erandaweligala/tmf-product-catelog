package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.*;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.adl.et.telco.mvno.productcatalog.domain.notification.ProductOfferingNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.processor.ProductOfferingProcessor;
import com.adl.et.telco.mvno.productcatalog.external.repositories.ProductOfferingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

class ProductOfferingServiceTest {


    ProductOfferingService service;
    ResourceRepositoryInterface<ProductOffering> mockRepository;
    MergeAdaptorInterface mockMergeAdaptor;
    ResourceProcessor<ProductOffering> mockProcessor;
    ResourceNotificationCreator<ProductOffering> mockNotificationCreator;
    NotifierAdaptorInterface mockNotifierAdaptor;
    HttpServletRequest httpServletRequest;


    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(ProductOfferingRepository.class);
        mockMergeAdaptor = Mockito.mock(MergeAdaptorInterface.class);
        mockProcessor = Mockito.mock(ProductOfferingProcessor.class);
        mockNotificationCreator = Mockito.mock(ProductOfferingNotificationCreator.class);
        mockNotifierAdaptor = Mockito.mock(NotifierAdaptorInterface.class);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);

        service = new ProductOfferingService(mockRepository, mockMergeAdaptor, mockProcessor,
                mockNotificationCreator, mockNotifierAdaptor,httpServletRequest);
    }

    @Test
    void query() {

        List<Filter> filters = Collections
                .singletonList(new Filter("name", new Object[]{"name"}, FilterOperation.IS));
        String fields = "name,description";

        ProductOffering offering1 = new ProductOffering();
        offering1.setId("id1");
        ProductOffering offering2 = new ProductOffering();
        offering2.setId("id2");

        Mockito.when(mockRepository.query(filters, fields, new Pageable()))
                .thenReturn(new Page<>(Arrays.asList(offering1, offering2), 2));

        Page<ProductOffering> page = service.query(filters, fields, new Pageable());

        Assertions.assertEquals(2, page.getTotal());
        Assertions.assertEquals(2, page.getContent().size());

        Mockito.verify(mockProcessor, Mockito.times(2)).postProcess(Mockito.any());

    }

    @Test
    void get() {

        ProductOffering offering = new ProductOffering();
        offering.setId("id");
        offering.setName("name");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(offering));

        ProductOffering saved = service.get("id");

        Assertions.assertEquals("name", saved.getName());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());

    }

    @Test
    void create() {


        ProductOffering offering = new ProductOffering();
        offering.setName("name");

        ProductOffering saved = new ProductOffering();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockRepository.save(Mockito.argThat(spec -> "name".equals(spec.getName()))))
                .thenReturn(saved);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.createNotifications(Mockito.any(ProductOffering.class)))
                .thenReturn(Collections.singletonList(notification));

        ProductOffering result = service.create(offering);

        Assertions.assertEquals(saved, result);

        Mockito.verify(mockProcessor, Mockito.times(1)).preCreateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }

    @Test
    void update() {

        ProductOffering saved = new ProductOffering();
        saved.setName("name");
        saved.setId("id");

        Map<String, Object> updateReq = new HashMap<>();
        updateReq.put("name", "name2");
        updateReq.put("description", "desc");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(saved));

        ProductOffering merged = new ProductOffering();
        merged.setName("name");
        merged.setDescription("desc");
        merged.setId("id");

        Mockito.when(mockMergeAdaptor.mergePatch(updateReq, saved, ProductOffering.class))
                .thenReturn(merged);

        Mockito.when(mockRepository.save(merged))
                .thenReturn(merged);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.updateNotifications(Mockito.eq(updateReq),
                        Mockito.any(ProductOffering.class), Mockito.any(ProductOffering.class),
                        Mockito.any(ProductOffering.class)))
                .thenReturn(Collections.singletonList(notification));

        ProductOffering result = service.update("id", updateReq);

        Assertions.assertEquals(merged, result);

        Mockito.verify(mockProcessor, Mockito.times(1))
                .preMergeProcess(Mockito.any(Map.class), Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).preUpdateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);
        
        
    }



    @Test
    void delete() {

        ProductOffering offering = new ProductOffering();
        offering.setId("id");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(offering));

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.deleteNotifications(offering))
                .thenReturn(Collections.singletonList(notification));

        service.delete("id");

        Mockito.verify(mockRepository, Mockito.times(1)).delete("id");
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }
}