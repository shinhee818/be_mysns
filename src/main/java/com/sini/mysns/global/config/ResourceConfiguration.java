package com.sini.mysns.global.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

    @Value("${upload.image.post}")
    private String uploadImagePath;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file://" + uploadImagePath);
    }
}
