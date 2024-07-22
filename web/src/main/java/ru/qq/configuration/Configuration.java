package ru.qq.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.qq.client.QRCodeServRestClient;
import ru.qq.client.impl.QRCodeServRestClientImpl;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${qr.api.uri}")
    private String qrApiUri;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public QRCodeServRestClientImpl qrCodeServRestClient(){
        return new QRCodeServRestClientImpl(RestClient.builder()
                .baseUrl(qrApiUri)
                .build());
    }
}
