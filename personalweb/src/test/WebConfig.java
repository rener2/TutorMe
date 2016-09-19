package com.personalweb.website;


import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!registry.hasMappingForPattern("/css/**")) {
                registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css");
            }
        }

    }


