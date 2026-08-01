package com.adl.et.telco.mvno.productcatalog.domain.processor;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.boundary.UrlResolverAdaptorInterface;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.PatchDef;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.exception.DomainException;
import com.adl.et.telco.dte.mvno.plugin.tmf.domain.processor.ResourceProcessor;
import com.adl.et.telco.mvno.productcatalog.domain.entities.CatalogResourceDocument;
import com.adl.et.telco.mvno.productcatalog.domain.service.utils.ServiceUtils;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AbstractResourceProcessor<T extends CatalogResourceDocument> implements ResourceProcessor<T> {

    private static final List<String> NON_PATCHABLE_ATTRIBS = Arrays.asList("id", "href", "lastUpdate");
    private final UrlResolverAdaptorInterface urlResolver;

    protected AbstractResourceProcessor(UrlResolverAdaptorInterface urlResolver) {
        this.urlResolver = urlResolver;
    }

    @Override
    public void preCreateProcess(T entity) {

        entity.setLastUpdate(OffsetDateTime.now());

        if (ServiceUtils.isInvalidTimePeriod(entity.getValidFor())) {

            throw new DomainException("Validity start time should be before validity end time",
                    "INVALID_TIME_PERIOD");
        }

        validate(entity);
    }

    @Override
    public void preMergeProcess(Map<String, Object> updateReq, T entity) {

       NON_PATCHABLE_ATTRIBS.forEach(updateReq::remove);

        if (ServiceUtils.checkVersionDecrement(entity.getVersion(), (String) updateReq.get("version"))) {
            throw new DomainException("New version should be greater than old version",
                    "NEW_VERSION_DECREMENT");
        }
    }

    @Override
    public void preMergeProcess(List<PatchDef> updateReq, T entity) {

        updateReq.removeIf(patchDef -> NON_PATCHABLE_ATTRIBS.contains(patchDef.getPath()));

        updateReq.stream().filter(patchDef -> "$.version".equals(patchDef.getPath()) && "replace".equals(patchDef.getOp()))
                .forEach(patchDef -> {
                    if (ServiceUtils.checkVersionDecrement(entity.getVersion(), patchDef.getValue().toString())) {
                        throw new DomainException("New version should be greater than old version",
                                "NEW_VERSION_DECREMENT");
                    }
                });
    }

    @Override
    public void preUpdateProcess(T entity) {

        preCreateProcess(entity);
    }

    @Override
    public void postProcess(T entity) {

        entity.setHref(urlResolver.createHref(entity));
    }

    protected abstract void validate(T entity);
}
