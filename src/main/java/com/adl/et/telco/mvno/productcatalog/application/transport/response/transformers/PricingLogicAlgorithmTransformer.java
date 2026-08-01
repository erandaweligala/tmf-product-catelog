package com.adl.et.telco.mvno.productcatalog.application.transport.response.transformers;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.PricingLogicAlgorithmRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.PricingLogicAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PricingLogicAlgorithmTransformer implements ResponseTransformer<PricingLogicAlgorithm> {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public PricingLogicAlgorithmTransformer(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper.copy();
        this.modelMapper = modelMapper;

        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public Map<String, Object> transform(PricingLogicAlgorithm entity) {

        return objectMapper.convertValue(modelMapper.map(entity, PricingLogicAlgorithmRequestEntity.class),
                objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
    }
}
