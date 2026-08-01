package com.adl.et.telco.mvno.productcatalog.domain.boundary;

import com.adl.et.telco.mvno.productcatalog.domain.entities.BaseSubResourceDocument;

public interface SubResourceRepositoryInterface<T extends BaseSubResourceDocument> {

    T save(T entity);

    boolean exists(String id);
}
