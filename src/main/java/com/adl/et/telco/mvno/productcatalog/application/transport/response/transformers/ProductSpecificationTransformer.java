package com.adl.et.telco.mvno.productcatalog.application.transport.response.transformers;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.mvno.productcatalog.application.transport.response.entities.ProductSpecificationResponseEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.ProductSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductSpecificationTransformer implements ResponseTransformer<ProductSpecification> {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public ProductSpecificationTransformer(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public Map<String, Object> transform(ProductSpecification entity) {

        return objectMapper.convertValue(modelMapper.map(entity, ProductSpecificationResponseEntity.class),
                objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
    }
}
