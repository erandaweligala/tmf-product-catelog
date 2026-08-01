package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.dte.mvno.plugin.tmf.external.repositories.AbstractResourceRepository;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class PricingLogicAlgorithmRepository extends AbstractResourceRepository<PricingLogicAlgorithm> {


    public PricingLogicAlgorithmRepository(MongoTemplate mongoTemplate,
                                           @Qualifier("queryExecutor") Executor queryExecutor) {

        super(mongoTemplate, PricingLogicAlgorithm.class, queryExecutor);
    }
}
