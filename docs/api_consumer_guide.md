# API consumer guide

## Query API

### HTTP request

GET {baseURL}/{resource}?limit={limit}&offset={offset}&fields={fields}&{filters}

Eg:
> http://localhost:8080/api/productSpecification?limit=10&offset=0&attachment.name=pic1,pic2&name=name&fields=name,attachment,description

| Parameter | Description | Example |
| ----------- | ----------- | --------- |
| limit | number of max items to receive. | 10 |
| offset | Offset count to skip items. | 20 |
| fields | Comma separated field names. Only supports top level fields. | name,description |
| filters | field level filters. | name=name&description=description&attachment.name=pic1,pic2 |

#### Supported filtering

- Multiple filters will be combined with AND.

    ex: ?name=name&description=description will return items that name is "name" and description is "description"
- Nested field matching is supported.
  
    ex: ?attachment.name=pic1 will return items that have attachments with name "pic1"
  
- Multiple values can be matched to one attribute with **OR** by providing comma separated values.
    
    **NOTE** : only supported by equals operator.
    ex: ?attachment.name=pic1,pic2 will return items that has attachment names with value "pic1" or "pic2"
- Cannot supply multiple boolean values to filter boolean valued fields.
- Date filtering is supported with Offset data time format.
  
    ex: ?lastUpdate=2021-06-01T11:39:32+01:00,2021-06-01T11:39:32-01:00,2021-06-01T11:39:32Z
- Following operators are supported.

| Operation | syntax | Supported types | Example |
| ----------- | ----------- | --------- | --------- |
| Equals | <field_name>.eq=<value> or <field_name>=<value> | String, Integer, Float, Boolean, Date | productSpecCharacteristic.maxCardinality=11 |
| Greater than | <field_name>.gt=<value> | Integer, Float, Date | productSpecCharacteristic.maxCardinality.gt=11 |
| Greater than or equals | <field_name>.gte=<value> | Integer, Float, Date | productSpecCharacteristic.maxCardinality.gte=11 |
| Less than | <field_name>.lt=<value> | Integer, Float, Date | productSpecCharacteristic.maxCardinality.lt=11 |
| Less than or equals | <field_name>.lte=<value> | Integer, Float, Date | productSpecCharacteristic.maxCardinality.lte=11 |
| Regex | <field_name>.regex=<value> | String | brand.regex=Cisco |

## Get one API

### HTTP request

GET {baseURL}/{resource}/{id}?fields={fields}

Eg:
> http://localhost:8080/api/productSpecification/60af164dc87b5a2d626ce0f3?fields=name,attachment,description

| Parameter | Description | Example |
| ----------- | ----------- | --------- |
| id | ID of the resource. | 60af164dc87b5a2d626ce0f3 |
| fields | Comma separated field names. Only supports top level fields. | name,description |

## Create API

### HTTP request

POST {baseURL}/{resource} with request body

Eg:
> http://localhost:8080/api/productSpecification

## Patch API

### HTTP request

PATCH {baseURL}/{resource}/{id} with request body

Eg:
> http://localhost:8080/api/productSpecification/60af164dc87b5a2d626ce0f3

| Parameter | Description | Example |
| ----------- | ----------- | --------- |
| id | ID of the resource. | 60af164dc87b5a2d626ce0f3 |

#### Supported patch protocols

[json/merge](https://tools.ietf.org/html/rfc7386)
  - To remove field value, pass null
  - To change field value, pass new value
  - To keep the same value, do not pass the field in request

## Delete API

### HTTP request

DELETE {baseURL}/{resource}/{id}

Eg:
> http://localhost:8080/api/productSpecification/60af164dc87b5a2d626ce0f3

| Parameter | Description | Example |
| ----------- | ----------- | --------- |
| id | ID of the resource. | 60af164dc87b5a2d626ce0f3 |
## Schema validation API

Schemas are stored in this catalog and a document can be validated against them without storing
it. Service Catalog Management validates the values of its specification characteristics through
this API instead of keeping its own copy of the schemas.

### HTTP request

POST {baseURL}/schema/validate with request body

Eg:
> http://localhost:31001/tmf-api/productCatalogManagement/v4/schema/validate

```json
{
  "schemaType": "ValueType",
  "name": "MobileService",
  "data": {
    "@baseType": "ValueType",
    "@type": "MobileService",
    "msisdn": "0771234567"
  }
}
```

| Field | Description |
| ----------- | ----------- |
| schemaType | Schema type of the schema. Optional, falls back to `@baseType` of `data`. When neither is given the schema is matched by name only. |
| name | Name of the schema. Optional, falls back to `@type` of `data`. |
| data | Document to validate. Required. |

### Response

The call answers 200 whenever the request itself is well formed. A document that breaks its
schema and a schema that is not loaded are both reported in the body, only a request without a
schema name or without data is answered with an error status.

```json
{
  "valid": false,
  "schemaFound": true,
  "schemaType": "ValueType",
  "name": "MobileService",
  "errors": ["$.msisdn - integer found, string expected"]
}
```

| Field | Description |
| ----------- | ----------- |
| valid | True when the document matches the schema. False when it does not, and when no schema was found. |
| schemaFound | False when no schema is stored for the given schema type and name. |
| errors | Validation errors, empty when the document is valid. |

## API documentation endpoints

The service documents itself with springdoc (OpenAPI 3). With the default `server.port: 31001`:

| URL | Purpose |
| ----------- | ----------- |
| http://localhost:31001/swagger-ui.html | Swagger UI. Use this URL - it redirects to `/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config` |
| http://localhost:31001/v3/api-docs | OpenAPI document as JSON |
| http://localhost:31001/v3/api-docs.yaml | OpenAPI document as YAML |
| http://localhost:31001/v3/api-docs/swagger-config | Bootstrap configuration that swagger-ui fetches |

These paths sit at the root of the service, they are not under
`app.context.absolute` (`/tmf-api/productCatalogManagement/v4`).

*Failed to load remote configuration* in the UI means the configuration URL could not be fetched.
Check the response of that URL directly - a 404 means springdoc is not on the classpath or is
disabled through `springdoc.api-docs.enabled`, and a 401/403 means something in front of the
service is blocking it.

### Why the configuration is served from a static file

springdoc answers `/v3/api-docs/swagger-config` from `SwaggerConfigResource.openapiJson`, a
`@GetMapping` handler whose last argument is the `HttpServletRequest` and whose return value is a
`Map`. `LoggingAdvice` in tmf-plugin 6.1 matches every such handler and casts the return value to
`ResponseEntity`, so the request fails with a `ClassCastException` and a 500:

```
java.lang.ClassCastException: class java.util.TreeMap cannot be cast to class
org.springframework.http.ResponseEntity
    at ...LoggingAdvice.logResponseAndHeader(LoggingAdvice.java:236)
    at org.springdoc.webmvc.ui.SwaggerConfigResource$$EnhancerBySpringCGLIB.openapiJson
```

`springdoc.swagger-ui.config-url` therefore points at `static/swagger-config.json`, which is
served as a plain resource and never reaches the advice. `/v3/api-docs` itself is unaffected: its
handler takes a `Locale` as the last argument, so the advice does not match it.

The advice is fixed in the plugin - it now only matches handlers in `com.adl..*` and no longer
requires a `ResponseEntity` return. Once a plugin release with that fix is in use, delete
`src/main/resources/static/swagger-config.json` and the `config-url` property.
