package com.cloth.clothshop.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ExternalRouteSettings implements WebMvcConfigurer {
    @Value("${pimgsave.dir}")
    private String imgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pimg/**")
                .addResourceLocations("file:///"+imgPath);

    }
}
