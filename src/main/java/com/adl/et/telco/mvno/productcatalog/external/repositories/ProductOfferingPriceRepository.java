package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Filter;
import com.adl.et.telco.dte.mvno.plugin.tmf.external.repositories.AbstractResourceRepository;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import com.adl.et.telco.mvno.productcatalog.external.repositories.utils.RepositoryUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

@Component
public class ProductOfferingPriceRepository extends AbstractResourceRepository<ProductOfferingPrice> {

    private final List<String> integerConversionList = Arrays.asList(
            "recurringChargePeriodLength",
            "prodSpecCharValueUse.bundledProductOfferingOption.numberRelOfferLowerLimit",
            "productSpecCharacteristic.productSpecCharRelationship.numberRelOfferUpperLimit",
            "prodSpecCharValueUse.maxCardinality",
            "prodSpecCharValueUse.minCardinality"
    );

    private final List<String> floatConversionList = Arrays.asList(
            "percentage",
            "productOfferingTerm.duration.amount",
            "tax.amount",
            "tax.taxAmount.value",
            "price.value",
            "unitOfMeasure.amount"
    );

    public ProductOfferingPriceRepository(MongoTemplate mongoTemplate,
                                          @Qualifier("queryExecutor") Executor queryExecutor) {

        super(mongoTemplate, ProductOfferingPrice.class, queryExecutor);
    }

    @Override
    protected void modifyFilters(List<Filter> filters) {

        filters.forEach(filter -> {

            if (("isBundle".equals(filter.getField()) ||
                    "prodSpecCharValueUse.productSpecCharacteristicValue.isDefault".equals(filter.getField())) &&
                    filter.getValue().length > 0) {

                filter.setValue(new Object[]{Boolean.valueOf((String) filter.getValue()[0])});
            }

            RepositoryUtils.convertFilterValueToDate(filter);

            // Float conversions.
            if (floatConversionList.contains(filter.getField())) {
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
