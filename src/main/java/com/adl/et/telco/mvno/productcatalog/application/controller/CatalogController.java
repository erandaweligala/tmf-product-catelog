package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.CatalogCreateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.CatalogUpdateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Catalog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.CATALOG_RESOURCE)
public class CatalogController extends BaseResourceController<Catalog,
        CatalogCreateRequestEntity,
        CatalogUpdateRequestEntity> {

    public CatalogController(BaseResourceService<Catalog> service,
                             ResponseTransformer<Catalog> transformer,
                             RequestEntityValidator validator) {


        super(service, transformer, validator, Catalog.class,
                CatalogUpdateRequestEntity.class);
    }
}
