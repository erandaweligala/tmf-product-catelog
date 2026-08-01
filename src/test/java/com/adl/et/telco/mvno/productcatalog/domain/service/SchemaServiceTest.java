package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.*;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SchemaEntityRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import com.adl.et.telco.mvno.productcatalog.domain.notification.SchemaNotificationCreator;
import com.adl.et.telco.mvno.productcatalog.domain.processor.SchemaProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchemaServiceTest {


    SchemaService service;
    SchemaEntityRepositoryInterface mockRepository;
    MergeAdaptorInterface mockMergeAdaptor;
    ResourceProcessor<SchemaEntity> mockProcessor;
    ResourceNotificationCreator<SchemaEntity> mockNotificationCreator;
    NotifierAdaptorInterface mockNotifierAdaptor;
    HttpServletRequest httpServletRequest;


    @InjectMocks
    private SchemaService schemaService;

    private String schemaType = "type1";
    private String name = "schema_name";


    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(SchemaEntityRepositoryInterface.class);
        mockMergeAdaptor = Mockito.mock(MergeAdaptorInterface.class);
        mockProcessor = Mockito.mock(SchemaProcessor.class);
        mockNotificationCreator = Mockito.mock(SchemaNotificationCreator.class);
        mockNotifierAdaptor = Mockito.mock(NotifierAdaptorInterface.class);
        schemaService=Mockito.mock(SchemaService.class);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);


        service = new SchemaService(mockRepository, mockMergeAdaptor, mockProcessor,
                mockNotificationCreator, mockNotifierAdaptor, httpServletRequest);
    }

    @Test
    void query() {

        List<Filter> filters = Collections
                .singletonList(new Filter("name", new Object[]{"name"}, FilterOperation.IS));
        String fields = "name,description";

        SchemaEntity schemaEntity1 = new SchemaEntity();
        schemaEntity1.setId("id1");
        SchemaEntity schemaEntity2 = new SchemaEntity();
        schemaEntity2.setId("id2");

        when(mockRepository.query(filters, fields, new Pageable()))
                .thenReturn(new Page<>(Arrays.asList(schemaEntity1, schemaEntity2), 2));

        Page<SchemaEntity> page = service.query(filters, fields, new Pageable());

        Assertions.assertEquals(2, page.getTotal());
        Assertions.assertEquals(2, page.getContent().size());
        verify(mockProcessor, Mockito.times(2)).postProcess(Mockito.any());

    }

    @Test
    void get() {

        SchemaEntity schemaEntity = new SchemaEntity();
        schemaEntity.setId("id");
        schemaEntity.setName("name");

        when(mockRepository.get("id"))
                .thenReturn(Optional.of(schemaEntity));

        SchemaEntity saved = service.get("id");

        Assertions.assertEquals("name", saved.getName());
        verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());

    }

    @Test
    void create() {


        SchemaEntity schemaEntity = new SchemaEntity();
        schemaEntity.setName("name");

        SchemaEntity saved = new SchemaEntity();
        saved.setName("name");
        saved.setId("id");

        when(mockRepository.save(Mockito.argThat(spec -> "name".equals(spec.getName()))))
                .thenReturn(saved);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        when(mockNotificationCreator.createNotifications(Mockito.any(SchemaEntity.class)))
                .thenReturn(Collections.singletonList(notification));

        SchemaEntity result = service.create(schemaEntity);

        Assertions.assertEquals(saved, result);

        verify(mockProcessor, Mockito.times(1)).preCreateProcess(Mockito.any());
        verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }

    @Test
    void update() {

        SchemaEntity saved = new SchemaEntity();
        saved.setName("name");
        saved.setId("id");

        Map<String, Object> updateReq = new HashMap<>();
        updateReq.put("name", "name2");
        updateReq.put("description", "desc");

        when(mockRepository.get("id"))
                .thenReturn(Optional.of(saved));

        SchemaEntity merged = new SchemaEntity();
        merged.setName("name");
        merged.setId("id");

        when(mockMergeAdaptor.mergePatch(updateReq, saved, SchemaEntity.class))
                .thenReturn(merged);

        when(mockRepository.save(merged))
                .thenReturn(merged);

        Notification notification = new Notification();
        notification.setEventId("abcd");

        when(mockNotificationCreator.updateNotifications(Mockito.eq(updateReq),
                        Mockito.any(SchemaEntity.class), Mockito.any(SchemaEntity.class),
                        Mockito.any(SchemaEntity.class)))
                .thenReturn(Collections.singletonList(notification));

        SchemaEntity result = service.update("id", updateReq);

        Assertions.assertEquals(merged, result);

        verify(mockProcessor, Mockito.times(1))
                .preMergeProcess(Mockito.any(Map.class), Mockito.any());
        verify(mockProcessor, Mockito.times(1)).preUpdateProcess(Mockito.any());
        verify(mockProcessor, Mockito.times(1)).postProcess(Mockito.any());
        verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);


    }



    @Test
    void delete() {

        SchemaEntity schemaEntity = new SchemaEntity();
        schemaEntity.setId("id");

        when(mockRepository.get("id"))
                .thenReturn(Optional.of(schemaEntity));

        Notification notification = new Notification();
        notification.setEventId("abcd");

        when(mockNotificationCreator.deleteNotifications(schemaEntity))
                .thenReturn(Collections.singletonList(notification));

        service.delete("id");

        verify(mockRepository, Mockito.times(1)).delete("id");
        verify(mockNotifierAdaptor, Mockito.times(1)).notify(notification);

    }

    @Test
    void testGetExistingSchemaEntity() {

        SchemaEntity existingEntity = new SchemaEntity();
        existingEntity.setSchemaType("type1");
        existingEntity.setName("schema_name");

        when(mockRepository.get("type1", "schema_name")).thenReturn(Optional.of(existingEntity));

        SchemaEntity result = service.get("type1", "schema_name");

        verify(mockRepository).get("type1", "schema_name");

        assertEquals(existingEntity, result);
    }

    @Test
    void testGetNonExistingSchemaEntity() {

        when(mockRepository.get("type1", "schema_name")).thenReturn(Optional.empty());

        schemaService.get("type1", "schema_name");

        assertEquals(schemaService.get(name),null );
    }

}