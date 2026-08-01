package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.PricingLogicAlgorithmRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.PRICING_LOGIC_ALGORITHM_SPECIFICATION_RESOURCE)
public class PricingLogicAlgorithmSpecificationController extends BaseResourceController<PricingLogicAlgorithm,
        PricingLogicAlgorithmRequestEntity,
        PricingLogicAlgorithmRequestEntity > {

    public PricingLogicAlgorithmSpecificationController(BaseResourceService<PricingLogicAlgorithm> service,
                                                        ResponseTransformer<PricingLogicAlgorithm> transformer,
                                                        RequestEntityValidator validator) {
        super(service, transformer, validator, PricingLogicAlgorithm.class,
                PricingLogicAlgorithmRequestEntity.class);
        //test
    }
}
