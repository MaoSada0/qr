package ru.qq.client;

import ru.qq.entity.QRCode;
import ru.qq.payload.QRCodeGetPayload;

import java.util.Optional;

public interface QRCodeGeneratorRestClient {

    Optional<byte[]> generateQRCode(QRCodeGetPayload qrCodeGetPayload);

}
