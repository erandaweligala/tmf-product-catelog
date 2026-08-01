package com.adl.et.telco.mvno.productcatalog.application.transport.response.transformers;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.mvno.productcatalog.application.transport.response.entities.ProductOfferingResponseEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductOffering;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductOfferingTransformer implements ResponseTransformer<ProductOffering> {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public ProductOfferingTransformer(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public Map<String, Object> transform(ProductOffering entity) {

        return objectMapper.convertValue(modelMapper.map(entity, ProductOfferingResponseEntity.class),
                objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
    }
}
