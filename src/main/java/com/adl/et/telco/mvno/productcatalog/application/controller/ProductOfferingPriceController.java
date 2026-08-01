package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.ProductOfferingPriceCreateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.ProductOfferingPriceUpdateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import com.adl.et.telco.mvno.productcatalog.domain.service.ProductOfferingPriceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.PRODUCT_OFFERING_PRICE_RESOURCE)
public class ProductOfferingPriceController extends BaseResourceController<ProductOfferingPrice,
        ProductOfferingPriceCreateRequestEntity,
        ProductOfferingPriceUpdateRequestEntity> {

    public ProductOfferingPriceController(ProductOfferingPriceService service,
                                          ResponseTransformer<ProductOfferingPrice> transformer,
                                          RequestEntityValidator validator) {

        super(service, transformer, validator, ProductOfferingPrice.class,
                ProductOfferingPriceUpdateRequestEntity.class);
    }
}
