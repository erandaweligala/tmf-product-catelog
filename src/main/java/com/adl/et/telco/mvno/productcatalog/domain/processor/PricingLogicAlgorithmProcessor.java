package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import org.springframework.stereotype.Component;

@Component
public class PricingLogicAlgorithmProcessor extends AbstractResourceProcessor<PricingLogicAlgorithm> {


    private final UrlResolverAdaptorInterface urlResolver;

    protected PricingLogicAlgorithmProcessor(UrlResolverAdaptorInterface urlResolver) {

        super(urlResolver);

        this.urlResolver = urlResolver;
    }

    @Override
    protected void validate(PricingLogicAlgorithm entity) {
        // empty
    }

    @Override
    public void postProcess(PricingLogicAlgorithm entity) {

        super.postProcess(entity);

        entity.setSchemaLocation(urlResolver.createHref(SchemaEntity.class, entity.getBaseType(), entity.getType()));
    }
}
