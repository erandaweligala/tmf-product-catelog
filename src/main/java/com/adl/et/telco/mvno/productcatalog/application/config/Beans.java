package com.adl.et.telco.mvno.productcatalog.application.config;

import com.adl.et.telco.dte.mvno.plugin.tmf.external.adaptors.util.TmfUrlConfig;
import com.adl.et.telco.mvno.productcatalog.domain.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setSkipNullEnabled(true);

        return mapper;
    }

    @Bean
    public TmfUrlConfig urlConfig() {

        return new TmfUrlConfig()
                .addConfig(ProductSpecification.class.getSimpleName(), Constants.UrlConstants.PRODUCT_SPECIFICATION_RESOURCE)
                .addConfig(Category.class.getSimpleName(), Constants.UrlConstants.CATEGORY_RESOURCE)
                .addConfig(Catalog.class.getSimpleName(), Constants.UrlConstants.CATALOG_RESOURCE)
                .addConfig(AttachmentRefOrValue.class.getSimpleName(), Constants.UrlConstants.ATTACHMENT_RESOURCE)
                .addConfig(AttachmentRefOrValueDocument.class.getSimpleName(), Constants.UrlConstants.ATTACHMENT_RESOURCE)
                .addConfig(ProductOffering.class.getSimpleName(), Constants.UrlConstants.PRODUCT_OFFERING_RESOURCE)
                .addConfig(ProductOfferingPrice.class.getSimpleName(), Constants.UrlConstants.PRODUCT_OFFERING_PRICE_RESOURCE)
                .addConfig("pricingLogicAlgorithmValue", Constants.UrlConstants.PRICING_LOGIC_ALGORITHM_RESOURCE)
                .addConfig(PricingLogicAlgorithm.class.getSimpleName(), Constants.UrlConstants.PRICING_LOGIC_ALGORITHM_SPECIFICATION_RESOURCE)
                .addConfig(SchemaEntity.class.getSimpleName(), Constants.UrlConstants.SCHEMA_RESOURCE);
    }
}
