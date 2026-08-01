package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.*;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import com.adl.et.telco.mvno.productcatalog.domain.notification.PricingLogicAlgorithmNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.processor.PricingLogicAlgorithmProcessor;
import com.adl.et.telco.mvno.productcatalog.external.repositories.PricingLogicAlgorithmRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

class PricingLogicAlgorithmSpecificationServiceTest {

    PricingLogicAlgorithmSpecificationService service;
    ResourceRepositoryInterface<PricingLogicAlgorithm> mockRepository;
    MergeAdaptorInterface mockMergeAdaptor;
    ResourceProcessor<PricingLogicAlgorithm> mockProcessor;
    ResourceNotificationCreator<PricingLogicAlgorithm> mockNotificationCreator;
    NotifierAdaptorInterface mockNotifierAdaptor;
    HttpServletRequest httpServletRequest;


    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(PricingLogicAlgorithmRepository.class);
        mockMergeAdaptor = Mockito.mock(MergeAdaptorInterface.class);
        mockProcessor = Mockito.mock(PricingLogicAlgorithmProcessor.class);
        mockNotificationCreator = Mockito.mock(PricingLogicAlgorithmNotificationCreator.class);
        mockNotifierAdaptor = Mockito.mock(NotifierAdaptorInterface.class);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);


        service = new PricingLogicAlgorithmSpecificationService(mockRepository, mockMergeAdaptor, mockProcessor,
                mockNotificationCreator, mockNotifierAdaptor,httpServletRequest);
    }

    @Test
    void query() {

        List<Filter> filters = Collections
                .singletonList(new Filter("name", new Object[]{"name"}, FilterOperation.IS));
        String fields = "name,description";

        PricingLogicAlgorithm pricingLogicAlgorithm1 = new PricingLogicAlgorithm();
        pricingLogicAlgorithm1.setId("id1");
        PricingLogicAlgorithm pricingLogicAlgorithm2 = new PricingLogicAlgorithm();
        pricingLogicAlgorithm2.setId("id2");

        Mockito.when(mockRepository.query(filters, fields, new Pageable()))
                .thenReturn(new Page<>(Arrays.asList(pricingLogicAlgorithm1, pricingLogicAlgorithm2), 2));

        Page<PricingLogicAlgorithm> page = service.query(filters, fields, new Pageable());

        Assertions.assertEquals(2, page.getTotal());
        Assertions.assertEquals(2, page.getContent().size());

        Mockito.verify(mockProcessor, Mockito.times(2)).postProcess(Mockito.any());

    }

    @Test
    void get() {

        PricingLogicAlgorithm pricingLogicAlgorithm = new PricingLogicAlgorithm();
        pricingLogicAlgorithm.setId("id");
        pricingLogicAlgorithm.setName("name");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(pricingLogicAlgorithm));

        PricingLogicAlgorithm saved = service.get("id");

        Assertions.assertEquals("name", saved.getName());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());

    }

    @Test
    void create() {


        PricingLogicAlgorithm pricingLogicAlgorithm = new PricingLogicAlgorithm();
        pricingLogicAlgorithm.setName("name");

        PricingLogicAlgorithm saved = new PricingLogicAlgorithm();
        saved.setName("name");
        saved.setId("id");

        Mockito.when(mockRepository.save(Mockito.argThat(spec -> "name".equals(spec.getName()))))
                .thenReturn(saved);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.createNotifications(Mockito.any(PricingLogicAlgorithm.class)))
                .thenReturn(Collections.singletonList(notification));

        PricingLogicAlgorithm result = service.create(pricingLogicAlgorithm);

        Assertions.assertEquals(saved, result);

        Mockito.verify(mockProcessor, Mockito.times(1)).preCreateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }

    @Test
    void update() {

        PricingLogicAlgorithm saved = new PricingLogicAlgorithm();
        saved.setName("name");
        saved.setId("id");

        Map<String, Object> updateReq = new HashMap<>();
        updateReq.put("name", "name2");
        updateReq.put("description", "desc");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(saved));

        PricingLogicAlgorithm merged = new PricingLogicAlgorithm();
        merged.setName("name");
        merged.setDescription("desc");
        merged.setId("id");

        Mockito.when(mockMergeAdaptor.mergePatch(updateReq, saved, PricingLogicAlgorithm.class))
                .thenReturn(merged);

        Mockito.when(mockRepository.save(merged))
                .thenReturn(merged);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.updateNotifications(Mockito.eq(updateReq),
                        Mockito.any(PricingLogicAlgorithm.class), Mockito.any(PricingLogicAlgorithm.class),
                        Mockito.any(PricingLogicAlgorithm.class)))
                .thenReturn(Collections.singletonList(notification));

        PricingLogicAlgorithm result = service.update("id", updateReq);

        Assertions.assertEquals(merged, result);

        Mockito.verify(mockProcessor, Mockito.times(1))
                .preMergeProcess(Mockito.any(Map.class), Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).preUpdateProcess(Mockito.any());
        Mockito.verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);


    }



    @Test
    void delete() {

        PricingLogicAlgorithm pricingLogicAlgorithm = new PricingLogicAlgorithm();
        pricingLogicAlgorithm.setId("id");

        Mockito.when(mockRepository.get("id"))
                .thenReturn(Optional.of(pricingLogicAlgorithm));

        Notification notification = new Notification();
        notification.setEventId("abcd");

        Mockito.when(mockNotificationCreator.deleteNotifications(pricingLogicAlgorithm))
                .thenReturn(Collections.singletonList(notification));

        service.delete("id");

        Mockito.verify(mockRepository, Mockito.times(1)).delete("id");
        Mockito.verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }
}