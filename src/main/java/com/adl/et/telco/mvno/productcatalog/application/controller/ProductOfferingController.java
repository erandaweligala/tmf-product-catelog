package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.ProductOfferingCreateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.ProductOfferingUpdateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.adl.et.telco.mvno.productcatalog.domain.service.ProductOfferingService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "productOffering")
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.PRODUCT_OFFERING_RESOURCE)
public class ProductOfferingController extends BaseResourceController<ProductOffering,
        ProductOfferingCreateRequestEntity,
        ProductOfferingUpdateRequestEntity> {

    public ProductOfferingController(ProductOfferingService service,
                                     ResponseTransformer<ProductOffering> transformer,
                                     RequestEntityValidator validator) {

        super(service, transformer, validator, ProductOffering.class,
                ProductOfferingUpdateRequestEntity.class);
    }
}
