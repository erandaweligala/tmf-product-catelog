package com.adl.et.telco.mvno.productcatalog.application.controller;

import com.adl.et.telco.dte.mvno.plugin.tmf.application.controller.BaseResourceController;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.transport.response.transformers.ResponseTransformer;
import com.adl.et.telco.dte.mvno.plugin.tmf.application.validator.RequestEntityValidator;
import com.adl.et.telco.mvno.productcatalog.application.config.Constants;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.SchemaCreateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.SchemaUpdateRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.request.entities.SchemaValidationRequestEntity;
import com.adl.et.telco.mvno.productcatalog.application.transport.response.entities.SchemaValidationResponseEntity;
import com.adl.et.telco.mvno.productcatalog.domain.dto.SchemaValidationResult;
import com.adl.et.telco.mvno.productcatalog.domain.entities.SchemaEntity;
import com.adl.et.telco.mvno.productcatalog.domain.service.SchemaService;
import com.adl.et.telco.mvno.productcatalog.domain.service.SchemaValidationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("${app.context.absolute}/" + Constants.UrlConstants.SCHEMA_RESOURCE)
public class SchemaController extends BaseResourceController<SchemaEntity,
        SchemaCreateRequestEntity,
        SchemaUpdateRequestEntity> {

    private static final String BASE_TYPE_FIELD = "@baseType";
    private static final String TYPE_FIELD = "@type";

    private final SchemaService service;
    private final SchemaValidationService validationService;

    public SchemaController(SchemaService service,
                            SchemaValidationService validationService,
                            ResponseTransformer<SchemaEntity> transformer,
                            RequestEntityValidator validator) {

        super(service, transformer, validator, SchemaEntity.class,
                SchemaUpdateRequestEntity.class);

        this.service = service;
        this.validationService = validationService;
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

    /**
     * Validate a JSON document against a stored schema. The schema is matched by schema type and
     * name, both of which fall back to the {@code @baseType} and {@code @type} of the document
     * when they are not sent, and by name only when no schema type is resolved.
     *
     * <p>The call answers 200 whenever the request itself is well formed. A document that breaks
     * its schema comes back with {@code valid} false and the errors, a schema that is not loaded
     * comes back with {@code schemaFound} false.</p>
     *
     * @param requestEntity document to validate together with the schema it is validated against.
     * @return Validation result.
     */
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "base_create", fallbackMethod = "circuitBreakerFallback")
    public ResponseEntity<SchemaValidationResponseEntity> validate(
            @RequestBody SchemaValidationRequestEntity requestEntity,
            HttpServletRequest request) {

        Map<String, Object> data = requestEntity.getData();

        String schemaType = resolve(requestEntity.getSchemaType(), data, BASE_TYPE_FIELD);
        String name = resolve(requestEntity.getName(), data, TYPE_FIELD);

        SchemaValidationResult result = validationService.validate(schemaType, name, data);

        SchemaValidationResponseEntity response = new SchemaValidationResponseEntity();

        response.setValid(result.isValid());
        response.setSchemaFound(result.isSchemaFound());
        response.setSchemaType(result.getSchemaType());
        response.setName(result.getName());
        response.setErrors(result.getErrors());

        return ResponseEntity.ok(response);
    }

    private String resolve(String value, Map<String, Object> data, String field) {

        if (Objects.nonNull(value) && !value.trim().isEmpty()) {
            return value;
        }

        if (Objects.isNull(data) || Objects.isNull(data.get(field))) {
            return null;
        }

        return data.get(field).toString();
    }
}
