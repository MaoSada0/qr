package ru.qq.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.qq.client.impl.QRCodeServRestClientImpl;
import ru.qq.security.OAuthClientHttpRequestInterceptor;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${qr.api.uri}")
    private String QR_API_URI;

    @Value("${qr.api.security.registration-id}")
    private String REGISTRATION_ID;

    @Value("${qr.api.security.password}")
    private String SECURITY_PASSWORD;

    @Value("${qr.api.security.username}")
    private String SECURITY_USERNAME;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public QRCodeServRestClientImpl qrCodeServRestClient(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository
    ){
        return new QRCodeServRestClientImpl(RestClient.builder()
                .baseUrl(QR_API_URI)
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientRepository),
                        REGISTRATION_ID
                ))
                .build());
    }
}
