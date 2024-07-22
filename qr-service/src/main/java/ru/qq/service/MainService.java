package ru.qq.service;

import ru.qq.entity.QRCode;
import ru.qq.payload.QRCodeGetPayload;

public interface MainService {
    QRCode saveQRCode(QRCodeGetPayload qrCodeGetPayload);
    QRCode getQRCodeById(Long id);
    void deleteQRCodeById(Long id);
//    QRCode updateQRCode(Long id);
}
