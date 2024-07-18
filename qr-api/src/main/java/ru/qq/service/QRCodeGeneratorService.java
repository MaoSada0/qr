package ru.qq.service;

import ru.qq.model.entity.QRCode;

public interface QRCodeGeneratorService {

    byte[] generateQrImage(QRCode qrCode);
}
