package com.personalweb.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Arrays;
import java.util.Properties;


@SpringBootApplication
@ImportResource("classpath:application-context.xml")
public class PersonalwebApplication {

	public static void main(String[] args) throws java.io.IOException{
       SpringApplication.run(PersonalwebApplication.class, args);
    }	}



