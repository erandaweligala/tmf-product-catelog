package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Filter;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Page;
import com.adl.et.telco.dte.mvno.plugin.tmf.external.repositories.AbstractResourceRepository;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import com.adl.et.telco.mvno.productcatalog.external.repositories.utils.RepositoryUtils;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Pageable;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

@Component
public class ProductSpecificationRepository extends AbstractResourceRepository<ProductSpecification> {

    List<String> booleanFields = Arrays.asList(
            "isBundle",
            "productSpecCharacteristic.productSpecCharacteristicValue.isDefault",
            "productSpecCharacteristic.configurable",
            "productSpecCharacteristic.extensible",
            "productSpecCharacteristic.isUnique"
    );

    public ProductSpecificationRepository(MongoTemplate mongoTemplate,
                                          @Qualifier("queryExecutor") Executor queryExecutor) {

        super(mongoTemplate, ProductSpecification.class, queryExecutor);
    }

    @Override
    protected void modifyFilters(List<Filter> filters) {

        filters.forEach(filter -> {
            // Boolean conversions.
            if (booleanFields.contains(filter.getField()) && filter.getValue().length > 0) {

                filter.setValue(new Object[]{Boolean.valueOf((String) filter.getValue()[0])});
            }

            // Datetime conversions.
            RepositoryUtils.convertFilterValueToDate(filter);

            // Float conversions.
            if ("attachment.size.amount".equals(filter.getField())) {
                filter.setValue(Arrays.stream(filter.getValue())
                        .map(o -> Float.valueOf((String) o))
                        .toArray());
            }

            // Integer conversions.
            if ("productSpecCharacteristic.maxCardinality".equals(filter.getField()) ||
                    "productSpecCharacteristic.minCardinality".equals(filter.getField()) ||
                    "productSpecCharacteristic.productSpecCharRelationship.charSpecSeq".equals(filter.getField())) {
                filter.setValue(Arrays.stream(filter.getValue())
                        .map(o -> Integer.valueOf((String) o))
                        .toArray());
            }
        });
    }



}
