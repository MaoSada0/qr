package ru.qq.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.qq.client.QRCodeGeneratorRestClient;
import ru.qq.client.impl.QRCodeGeneratorRestClientImpl;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${qr.api.uri}")
    private String qrApiUri;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpHeaders httpHeadersPng(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);

        return httpHeaders;
    }

    @Bean
    public QRCodeGeneratorRestClientImpl qrCodeGeneratorRestClient(){
        return new QRCodeGeneratorRestClientImpl(RestClient.builder()
                .baseUrl(qrApiUri)
                .build());
    }
}
