package com.adl.et.telco.mvno.productcatalog.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration of the service. The pooled {@code RestTemplate} and the {@code queryExecutor}
 * used by the repositories are contributed by tmf-plugin, only the CORS rules are defined here.
 */
@Slf4j
@Configuration
public class WebConfiguration {

    /**
     * CORS rules of every endpoint of the service, so that a browser application served from
     * another origin can call the API.
     *
     * <p>The paged endpoints answer the counts in {@code X-Total-Count} and
     * {@code X-Result-Count}, and a browser only reads response headers that are exposed, so
     * those two are exposed by default.</p>
     *
     * <p>{@code cors.allowed-origins} defaults to every origin, which suits an API that is
     * reached through a gateway. Narrow it to the origins of the applications that call this
     * service per environment. Credentials are off, a browser therefore does not send cookies
     * or authorization headers of its own with these calls, turn
     * {@code cors.allow-credentials} on together with a list of concrete origins when they are
     * needed.</p>
     */
    @Bean
    public WebMvcConfigurer corsConfigurer(
            @Value("${cors.allowed-origins:*}") String[] allowedOrigins,
            @Value("${cors.allowed-methods:GET,POST,PATCH,PUT,DELETE,OPTIONS,HEAD}") String[] allowedMethods,
            @Value("${cors.allowed-headers:*}") String[] allowedHeaders,
            @Value("${cors.exposed-headers:X-Total-Count,X-Result-Count}") String[] exposedHeaders,
            @Value("${cors.allow-credentials:false}") boolean allowCredentials,
            @Value("${cors.max-age:1800}") long maxAge) {

        log.info("CORS allowed origins : {}", String.join(", ", allowedOrigins));

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOriginPatterns(allowedOrigins)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .exposedHeaders(exposedHeaders)
                        .allowCredentials(allowCredentials)
                        .maxAge(maxAge);
            }
        };
    }
}
