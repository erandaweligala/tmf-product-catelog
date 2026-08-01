package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.*;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Catalog;
import com.adl.et.telco.mvno.productcatalog.domain.notification.CatalogNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.processor.CatalogProcessor;
import com.adl.et.telco.mvno.productcatalog.external.repositories.CatalogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CatalogServiceTest {

    CatalogService service;
    ResourceRepositoryInterface<Catalog> mockRepository;
    MergeAdaptorInterface mockMergeAdaptor;
    ResourceProcessor<Catalog> mockProcessor;
    ResourceNotificationCreator<Catalog> mockNotificationCreator;
    NotifierAdaptorInterface mockNotifierAdaptor;
    HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(CatalogRepository.class);
        mockMergeAdaptor = Mockito.mock(MergeAdaptorInterface.class);
        mockProcessor = Mockito.mock(CatalogProcessor.class);
        mockNotificationCreator = Mockito.mock(CatalogNotificationCreator.class);
        mockNotifierAdaptor = Mockito.mock(NotifierAdaptorInterface.class);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        service = new CatalogService(mockRepository, mockMergeAdaptor, mockProcessor,
                mockNotificationCreator, mockNotifierAdaptor, httpServletRequest);
    }

    @Test
    void query() {

        List<Filter> filters = Collections
                .singletonList(new Filter("name", new Object[]{"name"}, FilterOperation.IS));
        String fields = "name,description";

        Catalog catalog1 = new Catalog();
        catalog1.setId("id1");
        Catalog catalog2 = new Catalog();
        catalog2.setId("id2");

        Mockito.when(mockRepository.query(filters, fields, new Pageable()))
                .thenReturn(new Page<>(Arrays.asList(catalog1, catalog2), 2));

        Page<Catalog> page = service.query(filters, fields, new Pageable());

        assertEquals(2, page.getTotal());
        assertEquals(2, page.getContent().size());

        Mockito.verify(mockProcessor, Mockito.times(2)).postProcess(Mockito.any());

    }

    @Test
    void get() {

        Catalog catalog = new Catalog();
        catalog.setId("id");
        catalog.setName("name");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(catalog));

        Catalog saved = service.get("id");

        Assertions.assertEquals("name", saved.getName());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());

    }

    @Test
    void create() {


        Catalog catalog = new Catalog();
        catalog.setName("name");

        Catalog saved = new Catalog();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockRepository.save(Mockito.argThat(spec -> "name".equals(spec.getName()))))
                .thenReturn(saved);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.createNotifications(Mockito.any(Catalog.class)))
                .thenReturn(Collections.singletonList(notification));

        Catalog result = service.create(catalog);

        Assertions.assertEquals(saved, result);

        Mockito.verify(mockProcessor, Mockito.times(1)).preCreateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }

    @Test
    void update() {

        Catalog saved = new Catalog();
        saved.setName("name");
        saved.setId("id");

        Map<String, Object> updateReq = new HashMap<>();
        updateReq.put("name", "name2");
        updateReq.put("description", "desc");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(saved));

        Catalog merged = new Catalog();
        merged.setName("name");
        merged.setDescription("desc");
        merged.setId("id");

        Mockito.when(mockMergeAdaptor.mergePatch(updateReq, saved, Catalog.class))
                .thenReturn(merged);

        Mockito.when(mockRepository.save(merged))
                .thenReturn(merged);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.updateNotifications(Mockito.eq(updateReq),
                        Mockito.any(Catalog.class), Mockito.any(Catalog.class),
                        Mockito.any(Catalog.class)))
                .thenReturn(Collections.singletonList(notification));

        Catalog result = service.update("id", updateReq);

        Assertions.assertEquals(merged, result);

        Mockito.verify(mockProcessor, Mockito.times(1))
                .preMergeProcess(Mockito.any(Map.class), Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).preUpdateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);


    }


    @Test
    void delete() {

        Catalog catalog = new Catalog();
        catalog.setId("id");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(catalog));

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.deleteNotifications(catalog))
                .thenReturn(Collections.singletonList(notification));

        service.delete("id");

        Mockito.verify(mockRepository, Mockito.times(1)).delete("id");
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);


    }

}