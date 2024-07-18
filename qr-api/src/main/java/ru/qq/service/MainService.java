package ru.qq.service;

import ru.qq.model.entity.QRCode;

import java.util.List;

public interface MainService {
    List<QRCode> getAllQRCode();
    QRCode addQRCode(QRCode qrCode);
    QRCode findQRCodeById(Long id);
}
