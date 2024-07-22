package ru.qq.service;

import ru.qq.payload.QRCodeGetPayload;

public interface QRCodeGeneratorService {

    byte[] generateQrImage(QRCodeGetPayload qrCode);
}
