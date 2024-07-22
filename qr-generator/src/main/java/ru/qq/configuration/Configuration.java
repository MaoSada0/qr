package ru.qq.configuration;

import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public HttpHeaders httpHeadersPng(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);

        return httpHeaders;
    }
}
