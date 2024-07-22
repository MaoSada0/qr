package ru.qq.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.qq.service.MainService;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    @Value("${qr.api.url.new}")
    private String qrApiUrlNew;

    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<Resource> getResponseFromApi(String text, Short size) {
        HttpHeaders tempHeaders = new HttpHeaders();
        tempHeaders.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>("text=" + text + "&size=" + size, tempHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(qrApiUrlNew, HttpMethod.POST, entity, byte[].class);
        byte[] qrCodeBytes =response.getBody();

        ByteArrayResource byteArrayResource = new ByteArrayResource(qrCodeBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }
}
