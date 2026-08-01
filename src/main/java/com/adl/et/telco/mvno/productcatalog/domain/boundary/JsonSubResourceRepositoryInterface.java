package com.adl.et.telco.mvno.productcatalog.domain.boundary;

import java.util.Map;

public interface JsonSubResourceRepositoryInterface {

    Map<String, Object> save(String name, Map<String, Object> json);

    Map<String, Object> get(String name, String id);

    boolean exists(String name, String id);
}
