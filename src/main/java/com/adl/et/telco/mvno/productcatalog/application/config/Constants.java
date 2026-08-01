package com.adl.et.telco.mvno.productcatalog.application.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UrlConstants {
        public static final String PRODUCT_SPECIFICATION_RESOURCE = "productSpecification";
        public static final String CATEGORY_RESOURCE = "category";
        public static final String CATALOG_RESOURCE = "catalog";
        public static final String ATTACHMENT_RESOURCE = "attachment";
        public static final String PRODUCT_OFFERING_RESOURCE = "productOffering";
        public static final String PRODUCT_OFFERING_PRICE_RESOURCE = "productOfferingPrice";
        public static final String PRICING_LOGIC_ALGORITHM_SPECIFICATION_RESOURCE = "pricingLogicAlgorithmSpecification";
        public static final String PRICING_LOGIC_ALGORITHM_RESOURCE = "pricingLogicAlgorithm";
        public static final String SCHEMA_RESOURCE = "schema";
        public static final String API_NOTIFICATION = "hub";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MongoConstants {

        public static final String COLLECTION = "idSequence";
    }
}
