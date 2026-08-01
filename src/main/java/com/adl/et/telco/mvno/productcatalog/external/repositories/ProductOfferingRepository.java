package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Filter;
import com.adl.et.telco.dte.mvno.plugin.tmf.external.repositories.AbstractResourceRepository;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.adl.et.telco.mvno.productcatalog.external.repositories.utils.RepositoryUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

@Component
public class ProductOfferingRepository extends AbstractResourceRepository<ProductOffering> {

    private final List<String> integerConversionList = Arrays.asList(
            "bundledProductOffering.bundledProductOfferingOption.numberRelOfferDefault",
            "bundledProductOffering.bundledProductOfferingOption.numberRelOfferLowerLimit",
            "productSpecCharacteristic.productSpecCharRelationship.numberRelOfferUpperLimit",
            "prodSpecCharValueUse.maxCardinality",
            "prodSpecCharValueUse.minCardinality"
    );

    public ProductOfferingRepository(MongoTemplate mongoTemplate,
                                     @Qualifier("queryExecutor") Executor queryExecutor) {

        super(mongoTemplate, ProductOffering.class, queryExecutor);
    }

    @Override
    protected void modifyFilters(List<Filter> filters) {

        filters.forEach(filter -> {

            if (("isBundle".equals(filter.getField()) ||
                    "isSellable".equals(filter.getField()) ||
                    "prodSpecCharValueUse.productSpecCharacteristicValue.isDefault".equals(filter.getField())) &&
                    filter.getValue().length > 0) {

                filter.setValue(new Object[]{Boolean.valueOf((String) filter.getValue()[0])});
            }

            RepositoryUtils.convertFilterValueToDate(filter);

            // Float conversions.
            if ("attachment.size.amount".equals(filter.getField()) ||
                    "productOfferingTerm.duration.amount".equals(filter.getField())) {
                filter.setValue(Arrays.stream(filter.getValue())
                        .map(o -> Float.valueOf((String) o))
                        .toArray());
            }

            // Integer conversions.
            if (integerConversionList.contains(filter.getField())) {

                filter.setValue(Arrays.stream(filter.getValue())
                        .map(o -> Integer.valueOf((String) o))
                        .toArray());
            }
        });
    }
}
