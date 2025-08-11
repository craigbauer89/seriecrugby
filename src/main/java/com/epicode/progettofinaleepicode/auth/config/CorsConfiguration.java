package com.epicode.progettofinaleepicode.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
// If you want to apply CORS settings globally to all controllers in your application and don't want to annotate every method
	
	
//    private static final String GET = "GET";
//    private static final String POST = "POST";
//    private static final String PUT = "PUT";
//    private static final String DELETE = "DELETE";
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods(GET, POST, PUT, DELETE)
//                        .allowedHeaders("*")
//                        .allowedOriginPatterns("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
}