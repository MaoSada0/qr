package ru.qq.service;

import ru.qq.entity.QRCode;

public interface QRCodeGeneratorService {

    byte[] generateQrImage(QRCode qrCode);
}
