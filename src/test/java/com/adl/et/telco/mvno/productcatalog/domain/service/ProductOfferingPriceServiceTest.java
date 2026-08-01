package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.*;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import com.adl.et.telco.mvno.productcatalog.domain.notification.ProductOfferingPriceNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.processor.ProductOfferingPriceProcessor;
import com.adl.et.telco.mvno.productcatalog.external.repositories.ProductOfferingPriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

class ProductOfferingPriceServiceTest {

    ProductOfferingPriceService service;
    ResourceRepositoryInterface<ProductOfferingPrice> mockRepository;
    MergeAdaptorInterface mockMergeAdaptor;
    ResourceProcessor<ProductOfferingPrice> mockProcessor;
    ResourceNotificationCreator<ProductOfferingPrice> mockNotificationCreator;
    NotifierAdaptorInterface mockNotifierAdaptor;
    HttpServletRequest httpServletRequest;


    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(ProductOfferingPriceRepository.class);
        mockMergeAdaptor = Mockito.mock(MergeAdaptorInterface.class);
        mockProcessor = Mockito.mock(ProductOfferingPriceProcessor.class);
        mockNotificationCreator = Mockito.mock(ProductOfferingPriceNotificationCreator.class);
        mockNotifierAdaptor = Mockito.mock(NotifierAdaptorInterface.class);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        service = new ProductOfferingPriceService(mockRepository, mockMergeAdaptor, mockProcessor,
                mockNotificationCreator, mockNotifierAdaptor, httpServletRequest);
    }

    @Test
    void query() {

        List<Filter> filters = Collections
                .singletonList(new Filter("name", new Object[]{"name"}, FilterOperation.IS));
        String fields = "name,description";

        ProductOfferingPrice offering1 = new ProductOfferingPrice();
        offering1.setId("id1");
        ProductOfferingPrice offering2 = new ProductOfferingPrice();
        offering2.setId("id2");

        Mockito.when(mockRepository.query(filters, fields, new Pageable()))
                .thenReturn(new Page<>(Arrays.asList(offering1, offering2), 2));

        Page<ProductOfferingPrice> page = service.query(filters, fields, new Pageable());

        Assertions.assertEquals(2, page.getTotal());
        Assertions.assertEquals(2, page.getContent().size());

        Mockito.verify(mockProcessor, Mockito.times(2)).postProcess(Mockito.any());

    }

    @Test
    void get() {

        ProductOfferingPrice offeringPrice = new ProductOfferingPrice();
        offeringPrice.setId("id");
        offeringPrice.setName("name");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(offeringPrice));

        ProductOfferingPrice saved = service.get("id");

        Assertions.assertEquals("name", saved.getName());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());

    }

    @Test
    void create() {


        ProductOfferingPrice offering = new ProductOfferingPrice();
        offering.setName("name");

        ProductOfferingPrice saved = new ProductOfferingPrice();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockRepository.save(Mockito.argThat(spec -> "name".equals(spec.getName()))))
                .thenReturn(saved);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.createNotifications(Mockito.any(ProductOfferingPrice.class)))
                .thenReturn(Collections.singletonList(notification));

        ProductOfferingPrice result = service.create(offering);

        Assertions.assertEquals(saved, result);

        Mockito.verify(mockProcessor, Mockito.times(1)).preCreateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }

    @Test
    void update() {

        ProductOfferingPrice saved = new ProductOfferingPrice();
        saved.setName("name");
        saved.setId("id");

        Map<String, Object> updateReq = new HashMap<>();
        updateReq.put("name", "name2");
        updateReq.put("description", "desc");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(saved));

        ProductOfferingPrice merged = new ProductOfferingPrice();
        merged.setName("name");
        merged.setDescription("desc");
        merged.setId("id");

        Mockito.when(mockMergeAdaptor.mergePatch(updateReq, saved, ProductOfferingPrice.class))
                .thenReturn(merged);

        Mockito.when(mockRepository.save(merged))
                .thenReturn(merged);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.updateNotifications(Mockito.eq(updateReq),
                        Mockito.any(ProductOfferingPrice.class), Mockito.any(ProductOfferingPrice.class),
                        Mockito.any(ProductOfferingPrice.class)))
                .thenReturn(Collections.singletonList(notification));

        ProductOfferingPrice result = service.update("id", updateReq);

        Assertions.assertEquals(merged, result);

        Mockito.verify(mockProcessor, Mockito.times(1))
                .preMergeProcess(Mockito.any(Map.class), Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).preUpdateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);


    }



    @Test
    void delete() {

        ProductOfferingPrice offering = new ProductOfferingPrice();
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