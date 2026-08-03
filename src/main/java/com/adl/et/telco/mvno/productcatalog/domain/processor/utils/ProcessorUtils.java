package com.adl.et.telco.mvno.productcatalog.domain.processor.utils;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.ResourceRepositoryInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.BaseResourceDocument;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.dte.plugin.logging.services.LoggingUtils;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.JsonSubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.boundary.SubResourceRepositoryInterface;
import com.adl.et.telco.mvno.productcatalog.domain.dto.SchemaValidationResult;
import com.adl.et.telco.mvno.productcatalog.domain.entities.BaseSubResourceDocument;
import com.adl.et.telco.mvno.productcatalog.domain.service.SchemaValidationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ProcessorUtils {

    private final Logger logger = LoggingUtils.getLogger(ProcessorUtils.class);

    private static final String INVALID_SUB_RESOURCE_CODE = "INVALID_SUB_RESOURCE_ID";
    private static final String WITH_ID_MSG_PART = " with ID ";
    private static final String NO_MSG_PART = "No ";

    private final ModelMapper mapper;
    private final UrlResolverAdaptorInterface urlResolver;
    private final SchemaValidationService schemaValidationService;
    private final HttpServletRequest httpServletRequest;

    public ProcessorUtils(ModelMapper mapper, UrlResolverAdaptorInterface urlResolver,
                          SchemaValidationService schemaValidationService, HttpServletRequest httpServletRequest) {

        this.mapper = mapper;
        this.urlResolver = urlResolver;
        this.schemaValidationService = schemaValidationService;
        this.httpServletRequest = httpServletRequest;
    }

    public <T extends BaseSubResourceDocument, D extends T> void updateRefAndDocument(List<T> subResources,
                                                                                      Class<D> documentType,
                                                                                      SubResourceRepositoryInterface<D> repository) {

        if (subResources != null) {
            subResources.forEach(subResource -> {
                if (subResource.getId() == null) {
                    D saved = repository.save(mapper.map(subResource, documentType));

                    subResource.setHref(urlResolver.createHref(documentType, saved.getId()));
                    subResource.setId(saved.getId());
                } else {
                    repository.save(mapper.map(subResource, documentType));
                    subResource.setHref(urlResolver.createHref(documentType, subResource.getId()));
                }
            });
        }
    }

    public void updateRefAndDocument(List<Map<String, Object>> subResources,
                                     String collection,
                                     JsonSubResourceRepositoryInterface repository) {
        if (Objects.nonNull(httpServletRequest.getHeader("catalogId")) && !httpServletRequest.getHeader("catalogId").equals("")) {
            collection = httpServletRequest.getHeader("catalogId").concat("_").concat(collection);
            logger.info("collectionName={}", collection);
        }

        if (subResources != null) {
            String finalCollection = collection;
            subResources.forEach(subResource -> {
                if (subResource.getOrDefault("id", null) == null) {
                    Map<String, Object> saved = repository.save(finalCollection, subResource);

                    subResource.put("href", urlResolver.createHref(finalCollection, saved.get("id").toString()));
                    subResource.put("id", saved.get("id"));
                } else {
                    if (!repository.exists(finalCollection, subResource.get("id").toString())) {

                        throw new DomainException(NO_MSG_PART + finalCollection +
                                WITH_ID_MSG_PART + subResource.get("id").toString(), INVALID_SUB_RESOURCE_CODE);
                    }

                }
            });
        }
    }

    public <T extends BaseSubResourceDocument, D extends BaseResourceDocument>
    void checkRef(List<T> subResources,
                  Class<D> documentType,
                  ResourceRepositoryInterface<D> repository) {

        if (subResources != null) {
            subResources.forEach(subResource -> {
                if (subResource.getId() != null && !repository.exists(subResource.getId())) {
                    throw new DomainException(NO_MSG_PART + documentType.getSimpleName() + WITH_ID_MSG_PART +
                            subResource.getId(), INVALID_SUB_RESOURCE_CODE);
                }
            });
        }
    }

    public <T extends BaseSubResourceDocument, D extends BaseResourceDocument>
    void checkRef(T subResource,
                  Class<D> documentType,
                  ResourceRepositoryInterface<D> repository) {

        if (subResource != null && subResource.getId() != null && !repository.exists(subResource.getId())) {
            throw new DomainException(NO_MSG_PART + documentType.getSimpleName() + WITH_ID_MSG_PART +
                    subResource.getId(), INVALID_SUB_RESOURCE_CODE);
        }
    }

    public void validateBySchema(Map<String, Object> json, String typeName) {

        if (!json.containsKey("@type") || !json.containsKey("@baseType")) {
            throw new DomainException("No @type or @baseType defined in " + typeName + " json");
        }

        validateBySchema(json, json.get("@baseType").toString(), json.get("@type").toString(), typeName);
    }

    public void validateBySchema(Map<String, Object> json, String baseType, String type, String typeName) {

        SchemaValidationResult result = schemaValidationService.validate(baseType, type, json);

        if (!result.isSchemaFound()) {
            throw new DomainException("No schema for " + typeName + " with types " + baseType + "/" + type);
        }

        if (!result.isValid()) {
            throw new DomainException("Invalid schema with errors for " + typeName +
                    " : " + String.join(", ", result.getErrors()));
        }
    }
}
