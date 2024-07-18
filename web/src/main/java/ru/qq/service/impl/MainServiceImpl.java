package ru.qq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.qq.service.MainService;

public class MainServiceImpl implements MainService {

    @Value("${qr.api.url}")
    private String qrApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<Resource> getResponseFromApi(String text, Short size) {
        String apiUrl = qrApiUrl + "?text=" + text + "&size=" + size;

        ResponseEntity<byte[]> response = restTemplate.getForEntity(apiUrl, byte[].class);
        byte[] qrCodeBytes;
        if (response.getStatusCode() == HttpStatus.OK) {
            qrCodeBytes = response.getBody();
        } else {
            qrCodeBytes = null;
        }

        ByteArrayResource byteArrayResource = new ByteArrayResource(qrCodeBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }
}
