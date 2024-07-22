package ru.qq.service;

import ru.qq.entity.QRCode;
import ru.qq.payload.QRCodeGetPayload;

public interface QRCodeGeneratorService {

    byte[] generateQrImage(QRCodeGetPayload qrCode);
}
