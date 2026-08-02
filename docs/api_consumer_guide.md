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

## Build requirements

The service is compiled for Java 11 (`java.version` in `pom.xml`), because tmf-plugin is a Java 11
artifact. Targeting Java 8 fails the build with:

```
bad class file: .../tmf-plugin-6.x.jar(.../BaseResourceController.class)
  class file has wrong version 55.0, should be 52.0
```

The image that builds the service therefore needs a JDK 11 or newer, and the image that runs it a
matching JRE.
