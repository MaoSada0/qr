package ru.qq.service;

import org.springframework.core.io.ByteArrayResource;
import ru.qq.entity.QRCode;

import java.util.List;

public interface MainService {
    List<QRCode> getAllQRCode();
    QRCode addQRCode(QRCode qrCode);
    QRCode findQRCodeById(Long id);
    ByteArrayResource getZipAllQr();
}
