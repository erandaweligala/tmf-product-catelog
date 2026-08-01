package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Filter;
import com.adl.et.telco.dte.mvno.plugin.tmf.external.repositories.AbstractResourceRepository;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import com.adl.et.telco.mvno.productcatalog.external.repositories.utils.RepositoryUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Component
public class CategoryRepository extends AbstractResourceRepository<Category> {

    public CategoryRepository(MongoTemplate mongoTemplate, @Qualifier("queryExecutor") Executor queryExecutor) {

        super(mongoTemplate, Category.class, queryExecutor);
    }

    @Override
    protected void modifyFilters(List<Filter> filters) {

        filters.forEach(RepositoryUtils::convertFilterValueToDate);
    }
}
