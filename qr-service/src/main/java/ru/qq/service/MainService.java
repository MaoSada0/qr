package ru.qq.service;

import org.springframework.core.io.ByteArrayResource;
import ru.qq.entity.QRCode;
import ru.qq.payload.QRCodeGetPayload;

import java.util.List;

public interface MainService {
    QRCode saveQRCode(QRCodeGetPayload qrCodeGetPayload);
    QRCode getQRCodeById(Long id);
    void deleteQRCodeById(Long id);

    List<QRCode> getAllQRCode();

    ByteArrayResource getZipAllQr();
//    QRCode updateQRCode(Long id);
}
