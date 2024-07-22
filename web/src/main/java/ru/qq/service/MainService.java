package ru.qq.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.qq.payload.QRCodeGetPayload;

public interface MainService {
    ResponseEntity<Resource> getResponseFromApi(QRCodeGetPayload qrCodeGetPayload);
}
