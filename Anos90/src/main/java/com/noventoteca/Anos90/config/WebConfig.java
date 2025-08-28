package com.noventoteca.Anos90.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/styles.css")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
        
        registry.addResourceHandler("/imagens/**")
                .addResourceLocations("classpath:/static/imagens/")
                .setCachePeriod(3600);
                
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }
}
