package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SchemaProcessor extends AbstractResourceProcessor<SchemaEntity> {

    private final UrlResolverAdaptorInterface urlResolver;

    protected SchemaProcessor(UrlResolverAdaptorInterface urlResolver) {

        super(urlResolver);

        this.urlResolver = urlResolver;
    }

    @Override
    protected void validate(SchemaEntity schema) {

        if (Objects.isNull(schema.getSchemaType())) {
            throw new DomainException(
                    "Should have schema type",
                    "INVALID_SCHEMA_OBJECT");
        } else if (Objects.isNull(schema.getName()) || Objects.isNull(schema.getSchema())) {
            throw new DomainException(
                    "Should have both schema name and schema body",
                    "INVALID_SCHEMA_OBJECT");
        }

        //need to validate there is no schema with the same name
    }

    @Override
    public void postProcess(SchemaEntity entity) {

        entity.setHref(urlResolver.createHref(SchemaEntity.class, entity.getSchemaType(), entity.getName()));
    }
}
