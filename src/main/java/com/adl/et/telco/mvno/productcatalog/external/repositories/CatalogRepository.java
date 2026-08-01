package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Filter;
import com.adl.et.telco.dte.mvno.plugin.tmf.external.repositories.AbstractResourceRepository;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Catalog;
import com.adl.et.telco.mvno.productcatalog.external.repositories.utils.RepositoryUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogRepository extends AbstractResourceRepository<Catalog> {

    public CatalogRepository(MongoTemplate mongoTemplate) {

        super(mongoTemplate, Catalog.class);
    }

    @Override
    protected void modifyFilters(List<Filter> filters) {

        filters.forEach(RepositoryUtils::convertFilterValueToDate);
    }
}
