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
## API documentation (Swagger)

The running service publishes its own API documentation, generated from the resource controllers.

| Endpoint | Description |
| ----------- | ----------- |
| /v2/api-docs | Swagger 2.0 definition in JSON |
| /swagger-ui.html | Swagger UI |

Eg:
> http://localhost:31001/swagger-ui.html

The title, description and version shown in the documentation come from `app.swagger.*` in the
active `application-{profile}.yml`. Set `app.swagger.enabled: false` to disable both endpoints in a
given environment.

**NOTE** : these endpoints are served from the server root, not from `app.context.absolute`.
The committed `docs/swagger.yaml` remains the TMF620 reference contract for the API.
