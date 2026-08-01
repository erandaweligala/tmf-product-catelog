package com.adl.et.telco.mvno.productcatalog.application.transport.response.transformers;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.mvno.productcatalog.application.transport.response.entities.SchemaResponseEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SchemaTransformer implements ResponseTransformer<SchemaEntity> {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public SchemaTransformer(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public Map<String, Object> transform(SchemaEntity entity) {

        return objectMapper.convertValue(modelMapper.map(entity, SchemaResponseEntity.class),
                objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
    }
}
