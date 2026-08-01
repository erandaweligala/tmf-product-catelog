package com.adl.et.telco.mvno.productcatalog.domain.service;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.MergeAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.NotifierAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.notification.ResourceNotificationCreator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SchemaEntityRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class SchemaService extends BaseResourceService<SchemaEntity> {

    private final SchemaEntityRepositoryInterface repository;

    protected SchemaService(SchemaEntityRepositoryInterface repository,
                            MergeAdaptorInterface mergeAdaptor,
                            ResourceProcessor<SchemaEntity> processor,
                            ResourceNotificationCreator<SchemaEntity> notificationCreator,
                            NotifierAdaptorInterface notifier, HttpServletRequest httpServletRequest) {

        super(repository, "SCHEMA", mergeAdaptor, processor, SchemaEntity.class, notificationCreator, notifier, httpServletRequest);

        this.repository = repository;
    }

    /**
     * Get schema by schema type and name.
     *
     * @param schemaType schema type of the resource.
     * @param name       name of the schema.
     * @return Matching resource.
     */
    public SchemaEntity get(String schemaType, String name) {

        // Throw exception if no entity found for given ID.

        return repository.get(schemaType, name).orElseThrow(() ->
                new DomainException("Schema not found for schema type and name"));
    }
}
