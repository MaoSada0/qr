package ru.qq.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.qq.client.QRCodeServRestClient;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.service.MainService;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    @Value("${qr.api.uri}")
    private String qrApiUrl;

    @Autowired
    private final QRCodeServRestClient qrCodeServRestClient;

    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<Resource> getResponseFromApi(QRCodeGetPayload qrCodeGetPayload) {
//        HttpHeaders tempHeaders = new HttpHeaders();
//        tempHeaders.set("Content-Type", "application/x-www-form-urlencoded");
//
//        HttpEntity<String> entity = new HttpEntity<>("text=" + text + "&size=" + size, tempHeaders);
//
//        ResponseEntity<byte[]> response = restTemplate.exchange(qrApiUrlNew, HttpMethod.POST, entity, byte[].class);
        byte[] qrCodeBytes = qrCodeServRestClient.createQRCode(qrCodeGetPayload);

        ByteArrayResource byteArrayResource = new ByteArrayResource(qrCodeBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
    }
}
