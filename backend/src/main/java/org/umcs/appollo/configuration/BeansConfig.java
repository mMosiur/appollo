package org.umcs.appollo.configuration;

import org.apache.tomcat.jni.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.umcs.appollo.converters.UserConverter;

@Configuration
@ComponentScan(basePackageClasses = UserConverter.class)
public class BeansConfig {
    @Bean
    public UserConverter getAddress() {
        return new UserConverter();
    }
}