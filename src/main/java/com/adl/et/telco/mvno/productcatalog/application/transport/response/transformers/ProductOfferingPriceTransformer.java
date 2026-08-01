package com.adl.et.telco.mvno.productcatalog.application.transport.response.transformers;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.mvno.productcatalog.application.transport.response.entities.ProductOfferingPriceResponseEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOfferingPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductOfferingPriceTransformer implements ResponseTransformer<ProductOfferingPrice> {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public ProductOfferingPriceTransformer(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public Map<String, Object> transform(ProductOfferingPrice entity) {

        return objectMapper.convertValue(modelMapper.map(entity, ProductOfferingPriceResponseEntity.class),
                objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
    }
}
