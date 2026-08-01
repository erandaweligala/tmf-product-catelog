package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.service.BaseResourceService;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.CategoryCreateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.CategoryUpdateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.Category;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "category")
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.CATEGORY_RESOURCE)
public class CategoryController extends BaseResourceController<Category,
        CategoryCreateRequestEntity,
        CategoryUpdateRequestEntity> {

    public CategoryController(BaseResourceService<Category> service,
                              ResponseTransformer<Category> transformer,
                              RequestEntityValidator validator) {

        super(service, transformer, validator, Category.class,
                CategoryUpdateRequestEntity.class);
    }
}
