package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.SchemaCreateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.SchemaUpdateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import com.adl.et.telco.mvno.productcatalog.domain.service.SchemaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.SCHEMA_RESOURCE)
public class SchemaController extends BaseResourceController<SchemaEntity,
        SchemaCreateRequestEntity,
        SchemaUpdateRequestEntity> {

    private final SchemaService service;

    public SchemaController(SchemaService service,
                            ResponseTransformer<SchemaEntity> transformer,
                            RequestEntityValidator validator) {

        super(service, transformer, validator, SchemaEntity.class,
                SchemaUpdateRequestEntity.class);

        this.service = service;
    }

    /**
     * Get schema entity by schema type and name.
     *
     * @param type schema type of the schema.
     * @param name name of the schema.
     * @return Schema.
     */
    @GetMapping(value = "/{type}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "base_get", fallbackMethod = "circuitBreakerFallback")
    public ResponseEntity<Map<String, Object>> getByTypeAndName(@PathVariable String type, @PathVariable String name,
                                                                HttpServletRequest request) {

        Map<String, Object> transformed = service.get(type, name).getSchema();

        return ResponseEntity.ok(transformed);
    }
}
