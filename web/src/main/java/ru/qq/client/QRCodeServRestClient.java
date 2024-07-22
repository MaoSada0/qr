package ru.qq.client;

import lombok.RequiredArgsConstructor;
import ru.qq.payload.QRCodeGetPayload;

import java.util.Optional;


public interface QRCodeServRestClient {

    byte[] createQRCode(QRCodeGetPayload qrCodeGetPayload);

}
