package com.adl.et.telco.mvno.productcatalog.domain.boundary;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;

import java.util.Optional;

public interface SchemaEntityRepositoryInterface extends ResourceRepositoryInterface<SchemaEntity> {

    Optional<SchemaEntity> get(String schemaType, String name);
}
