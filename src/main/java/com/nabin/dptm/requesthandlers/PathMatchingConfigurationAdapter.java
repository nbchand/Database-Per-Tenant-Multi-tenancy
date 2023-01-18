package com.nabin.dptm.requesthandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Custom WebMvcConfigurer
 *
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@Configuration
@RequiredArgsConstructor
public class PathMatchingConfigurationAdapter implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;

    /**
     * Adding our custom request interceptor to the registry
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
    }
}
