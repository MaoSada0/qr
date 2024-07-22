package ru.qq.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.qq.client.QRCodeGeneratorRestClient;
import ru.qq.payload.QRCodeGetPayload;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class QRCodeGeneratorRestClientImpl implements QRCodeGeneratorRestClient {

    private final RestClient restClient;

    @Override
    public Optional<byte[]> generateQRCode(QRCodeGetPayload qrCodeGetPayload) {
        try {
            return Optional.ofNullable(restClient.get()
                    .uri("?text=" + qrCodeGetPayload.data() + "&size=" + qrCodeGetPayload.size())
                    .retrieve()
                    .body(byte[].class));
        } catch (HttpClientErrorException.BadRequest exception){
            return Optional.empty();
        }

    }
}
