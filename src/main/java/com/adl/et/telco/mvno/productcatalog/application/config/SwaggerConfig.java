/*
  Copyrights 2020 Axiata Digital Labs Pvt Ltd.
  All Rights Reserved.

  These material are unpublished, proprietary, confidential source
  code of Axiata Digital Labs Pvt Ltd (ADL) and constitute a TRADE
  SECRET of ADL.

  ADL retains all title to and intellectual property rights in these
  materials.

 */
package com.adl.et.telco.mvno.productcatalog.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Exposes the API documentation of the resource controllers.
 *
 * <ul>
 *     <li>Swagger definition : {@code /v2/api-docs}</li>
 *     <li>Swagger UI : {@code /swagger-ui.html}</li>
 * </ul>
 *
 * Documentation can be switched off per environment with {@code app.swagger.enabled: false}.
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "app.swagger", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String BASE_PACKAGE = "com.adl.et.telco";

    private final String title;
    private final String description;
    private final String version;
    private final String contextPath;

    public SwaggerConfig(@Value("${app.swagger.title}") String title,
                         @Value("${app.swagger.description}") String description,
                         @Value("${app.swagger.version}") String version,
                         @Value("${app.context.absolute}") String contextPath) {

        this.title = title;
        this.description = description;
        this.version = version;
        this.contextPath = contextPath;
    }

    @Bean
    public Docket productCatalogApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.ant(contextPath + "/**"))
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

    /**
     * Springfox 2.x ships the UI assets inside the jar, they have to be mapped explicitly so that
     * the UI is reachable when the application defines its own resource handling.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
