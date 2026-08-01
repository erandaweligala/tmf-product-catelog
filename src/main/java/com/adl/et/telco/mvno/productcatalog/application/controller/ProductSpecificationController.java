package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.ProductSpecificationCreateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.ProductSpecificationUpdateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import com.adl.et.telco.mvno.productcatalog.domain.service.ProductSpecificationService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "productSpecification")
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.PRODUCT_SPECIFICATION_RESOURCE)
public class ProductSpecificationController extends BaseResourceController<ProductSpecification,
        ProductSpecificationCreateRequestEntity,
        ProductSpecificationUpdateRequestEntity> {

    public ProductSpecificationController(ProductSpecificationService service,
                                          ResponseTransformer<ProductSpecification> transformer,
                                          RequestEntityValidator validator) {

        super(service, transformer, validator, ProductSpecification.class,
                ProductSpecificationUpdateRequestEntity.class);
    }
}
